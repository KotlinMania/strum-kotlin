import Testing
import Strum

@Suite("Strum Swift Export Tests")
struct StrumExportTests {
    @Test("Strum Swift module imports cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true))
    }
}
