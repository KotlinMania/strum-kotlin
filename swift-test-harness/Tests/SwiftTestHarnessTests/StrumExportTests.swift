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
        #expect(ParseError.allCases.count == 1)
    }
}
