"""Compile typed Strum contracts against an explicitly captured producer classpath."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import subprocess


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--classpath-receipt", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    root = Path(__file__).resolve().parent
    output = args.output.resolve()
    output.mkdir(parents=True, exist_ok=False)
    data = json.loads(args.classpath_receipt.read_text())
    compiler = [Path(p) for p in data["compiler"]]
    dependencies = [Path(data["jar"])] + [Path(p) for p in data["dependencies"]]
    assert any(p.name == "kotlin-compiler-embeddable-2.4.20.jar" for p in compiler)
    inputs = compiler + dependencies + list(root.glob("*.kt")) + [Path(__file__), args.classpath_receipt]
    frozen = {str(p): digest(p) for p in inputs}
    (output / "frozen-inputs.json").write_text(json.dumps(frozen, indent=2) + "\n")
    java = Path(os.environ["JAVA_HOME"]) / "bin/java"
    cases = {
        "Positive": None,
        "WrongElement": "UPPER_BOUND_VIOLATED",
        "WrongDiscriminant": "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
        "WrongReference": "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
        "WrongVariants": "PROPERTY_TYPE_MISMATCH_ON_OVERRIDE",
        "WrongVariantIterator": "UPPER_BOUND_VIOLATED",
        "MissingReverse": "ABSTRACT_MEMBER_NOT_IMPLEMENTED",
        "WrongAssociatedClone": "UPPER_BOUND_VIOLATED",
    }
    results = {}
    for name, expected in cases.items():
        destination = output / name
        destination.mkdir()
        command = [str(java), "-Xmx512m", "-cp", os.pathsep.join(map(str, compiler)),
                   "org.jetbrains.kotlin.cli.jvm.K2JVMCompiler", "-no-stdlib", "-no-reflect",
                   "-jvm-target", "21", "-Werror", "-Xrender-internal-diagnostic-names",
                   "-classpath", os.pathsep.join(map(str, dependencies)), "-d", str(destination),
                   str(root / (name + ".kt"))]
        with (output / (name + ".log")).open("w") as log:
            process = subprocess.Popen(command, stdout=log, stderr=subprocess.STDOUT)
            (output / "active-process.json").write_text(json.dumps({"pid": process.pid, "case": name, "active": True}))
            code = process.wait()
        text = (output / (name + ".log")).read_text()
        diagnostics = re.findall(r"\[([A-Z_]+)\]", text)
        (output / "active-process.json").write_text(json.dumps({"pid": process.pid, "case": name, "active": False, "exit": code}))
        results[name] = {"exit": code, "diagnostics": diagnostics, "expected": expected, "command": command}
        (output / "case-results.json").write_text(json.dumps(results, indent=2) + "\n")
        if expected is None:
            assert code == 0 and not diagnostics, (name, text)
        else:
            assert code != 0 and expected in diagnostics, (name, text)
            assert not any("UNRESOLVED" in diagnostic for diagnostic in diagnostics), (name, text)
    for path, value in frozen.items():
        assert digest(Path(path)) == value, path
    result = {"positive_cases": 1, "negative_cases": 7, "all_expected": True,
              "producer_jar_sha256": digest(Path(data["jar"])), "warnings_as_errors": True,
              "source_and_classpath_unchanged": True,
              "scope": "Actual Kotlin/JVM compiler contracts, not runtime or published-consumer acceptance"}
    (output / "verified-result.json").write_text(json.dumps(result, indent=2) + "\n")
    print(json.dumps(result))


if __name__ == "__main__":
    main()
