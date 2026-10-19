import Testing
import Strum

@Suite("Strum Swift Export Tests")
struct StrumExportTests {
    @Test("Strum Swift module imports cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true))
    }

    @Test("ParseError export works")
    func parseErrorExport() {
        let err = ParseError.VARIANT_NOT_FOUND
        #expect(err.rawValue == 0)
        #expect(err.description == "VARIANT_NOT_FOUND")
        #expect(err.fmt() == "Matching variant not found")
        #expect(err.toString() == "Matching variant not found")
        #expect(description(err).contains("Unable to find a variant"))
        #expect(ParseError.allCases.count == 1)
    }

    @Test("CaseStyle and convertCase export works")
    func caseStyleExport() {
        #expect(CaseStyle.SNAKE_CASE.convert(input: "HelloWorld") == "hello_world")
        #expect(CaseStyle.KEBAB_CASE.convert(input: "HelloWorld") == "hello-world")
        #expect(convertCase(input: "hello_world", styleName: "PascalCase") == "HelloWorld")
        #expect(CaseStyle.Companion.shared.fromStyleName(name: "snake_case") == CaseStyle.SNAKE_CASE)
    }

    @Test("StrumEntry export works")
    func strumEntryExport() {
        let entry = StrumEntry(
            message: "msg",
            detailedMessage: "detail",
            documentation: "doc",
            serializations: ["s1", "s2"],
            properties: [:]
        )
        #expect(entry.getMessage() == "msg")
        #expect(entry.getDetailedMessage() == "detail")
        #expect(entry.getDocumentation() == "doc")
        #expect(entry.getSerializations() == ["s1", "s2"])
    }
}
