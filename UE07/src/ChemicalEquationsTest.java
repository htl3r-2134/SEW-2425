import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChemicalEquationsTest {

    @Test
    void testWaterDecomposition() {
        assertTrue(ChemicalEquations.analyze("H2O -> O + H2"));
    }

    @Test
    void testWaterFormation() {
        assertTrue(ChemicalEquations.analyze("O + H2 -> H2O"));
    }

    @Test
    void testWaterFormationWithAtomicHydrogen() {
        assertTrue(ChemicalEquations.analyze("O + 2 H -> H2O"));
    }

    @Test
    void testWaterFormationWithSeparateAtoms() {
        assertTrue(ChemicalEquations.analyze("O + H + H -> H2O"));
    }

    @Test
    void testUnbalancedHydrochloricAcidReaction() {
        assertFalse(ChemicalEquations.analyze("HCl + Na -> NaCl + H2"));
    }

    @Test
    void testBalancedHydrochloricAcidReaction() {
        assertTrue(ChemicalEquations.analyze("2 HCl + 2 Na -> 2 NaCl + H2"));
    }

    @Test
    void testUnbalancedPhotosynthesis() {
        assertFalse(ChemicalEquations.analyze("12 CO2 + 6 H2O -> 2 C6H12O6 + 12 O2"));
    }

    @Test
    void testSimpleWaterFormation() {
        assertTrue(ChemicalEquations.analyze("2 H2 + O2 -> 2 H2O"));
    }

    @Test
    void testSimpleWaterDecomposition() {
        assertTrue(ChemicalEquations.analyze("2 H2O -> 2 H2 + O2"));
    }

    @Test
    void testUnbalancedWaterFormation() {
        assertFalse(ChemicalEquations.analyze("H2 + O2 -> H2O"));
    }

    @Test
    void testMethaneFormation() {
        assertTrue(ChemicalEquations.analyze("C + 2 H2 -> CH4"));
    }

    @Test
    void testUnbalancedMethaneFormation() {
        assertFalse(ChemicalEquations.analyze("C + H2 -> CH4"));
    }

    @Test
    void testAmmoniaFormation() {
        assertTrue(ChemicalEquations.analyze("N2 + 3 H2 -> 2 NH3"));
    }

    @Test
    void testCarbonDioxideFormation() {
        assertTrue(ChemicalEquations.analyze("C + O2 -> CO2"));
    }

    @Test
    void testSulfuricAcidFormation() {
        assertTrue(ChemicalEquations.analyze("2 SO2 + O2 + 2 H2O -> 2 H2SO4"));
    }

    @Test
    void testUnbalancedSulfuricAcidFormation() {
        assertFalse(ChemicalEquations.analyze("SO2 + O2 + H2O -> H2SO4"));
    }

    @Test
    void testCalciumCarbonateDecomposition() {
        assertTrue(ChemicalEquations.analyze("CaCO3 -> CaO + CO2"));
    }

    @Test
    void testIronOxideFormation() {
        assertTrue(ChemicalEquations.analyze("4 Fe + 3 O2 -> 2 Fe2O3"));
    }

    @Test
    void testUnbalancedIronOxideFormation() {
        assertFalse(ChemicalEquations.analyze("Fe + O2 -> Fe2O3"));
    }

    @Test
    void testComplexMolecule() {
        assertTrue(ChemicalEquations.analyze("C6H12O6 + 6 O2 -> 6 CO2 + 6 H2O"));
    }

    @Test
    void testUnbalancedComplexMolecule() {
        assertFalse(ChemicalEquations.analyze("C6H12O6 + O2 -> CO2 + H2O"));
    }

    @Test
    void testSingleElementReaction() {
        assertTrue(ChemicalEquations.analyze("2 H2 + O2 -> 2 H2O"));
    }

    @Test
    void testLargeCoefficients() {
        assertTrue(ChemicalEquations.analyze("12 H2 + 6 O2 -> 12 H2O"));
    }

    @Test
    void testInvalidFormula() {
        assertFalse(ChemicalEquations.analyze("H2O + > H2 + O"));
    }

    @Test
    void testMissingArrow() {
        assertFalse(ChemicalEquations.analyze("H2 + O2 H2O"));
    }

    @Test
    void testEmptyString() {
        assertFalse(ChemicalEquations.analyze(""));
    }

}
