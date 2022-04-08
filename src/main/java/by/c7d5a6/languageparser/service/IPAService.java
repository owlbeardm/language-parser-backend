package by.c7d5a6.languageparser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IPAService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //Pulmonic consonants
    //Manner
    private final String[] plumonicConsonantsNasal = {"m̥", "m", "ɱ", "n̼", "n̥", "n", "ɳ̊", "ɳ", "ɲ̊", "ɲ", "ŋ̊", "ŋ", "ɴ"};
    private final String[] plumonicConsonantsPlosive = {"p", "b", "p̪", "b̪", "t̼", "d̼", "t", "d", "ʈ", "ɖ", "c", "ɟ", "k", "ɡ", "q", "ɢ", "ʡ", "ʔ"};
    private final String[] plumonicConsonantsSibilantFricative = {"s", "z", "ʃ", "ʒ", "ʂ", "ʐ", "ɕ", "ʑ"};
    private final String[] plumonicConsonantsNonSibilantFricative = {"ɸ", "β", "f", "v", "θ̼", "ð̼", "θ", "ð", "θ̠", "ð̠", "ɹ̠̊˔", "ɹ̠˔", "", "ɻ˔", "ç", "ʝ", "x", "ɣ", "χ", "ʁ", "ħ", "ʕ", "h", "ɦ"};
    private final String[] plumonicConsonantsApproximant = {"ʋ", "ɹ", "ɻ", "j", "ɰ", "ʔ̞"};
    private final String[] plumonicConsonantsTapFlap = {"ⱱ̟", "ⱱ", "ɾ̼", "ɾ̥", "ɾ", "ɽ̊", "ɽ", "ɢ̆", "ʡ̆"};
    private final String[] plumonicConsonantsTrill = {"ʙ̥", "ʙ", "r̥", "r", "ɽ̊r̥", "ɽr", "ʀ̥", "ʀ", "ʜ", "ʢ"};
    private final String[] plumonicConsonantsLateralFricative = {"ɬ", "ɮ", "ɭ̊˔", "ɭ˔", "ʎ̝̊", "ʎ̝", "ʟ̝̊", "ʟ̝"};
    private final String[] plumonicConsonantsLateralApproximant = {"l̥", "l", "ɭ", "ʎ", "ʟ", "ʟ̠"};
    private final String[] plumonicConsonantsLateralTapFlap = {"ɺ̥", "ɺ", "ɭ̥̆", "ɭ̆", "ʎ̝̆̊", "ʟ̆"};
    private final String[] PlumonicConsonants = concatenate(
            plumonicConsonantsNasal,
            plumonicConsonantsPlosive,
            plumonicConsonantsSibilantFricative,
            plumonicConsonantsNonSibilantFricative,
            plumonicConsonantsApproximant,
            plumonicConsonantsTapFlap,
            plumonicConsonantsTrill,
            plumonicConsonantsLateralFricative,
            plumonicConsonantsLateralApproximant,
            plumonicConsonantsLateralTapFlap);
    // Non-pulmonic consonants
    // Manner
    private final String[] ejectiveStop = {"pʼ", "tʼ", "ʈʼ", "cʼ", "kʼ", "qʼ", "ʡʼ"};
    private final String[] ejectiveFricative = {"ɸʼ", "fʼ", "θʼ", "sʼ", "ʃʼ", "ʂʼ", "ɕʼ", "xʼ", "χʼ"};
    private final String[] ejectiveLateralFricative = {"ɬʼ"};
    private final String[] ejective = concatenate(
            ejectiveStop,
            ejectiveFricative,
            ejectiveLateralFricative);
    private final String[] clickTenuis = {"kʘ", "qʘ", "kǀ", "qǀ", "kǃ", "qǃ", "k‼", "q‼", "kǂ", "qǂ"};
    private final String[] clickVoiced = {"ɡʘ", "ɢʘ", "ɡǀ", "ɢǀ", "ɡǃ", "ɢǃ", "ɡ‼", "ɢ‼", "ɡǂ", "ɢǂ"};
    private final String[] clickNasal = {"ŋʘ", "ɴʘ", "ŋǀ", "ɴǀ", "ŋǃ", "ɴǃ", "ŋ‼", "ɴ‼", "ŋǂ", "ɴǂ", "ʞ"};
    private final String[] clickTenuisLateral = {"kǁ", "qǁ"};
    private final String[] clickVoicedLateral = {"ɡǁ", "ɢǁ"};
    private final String[] clickNasalLateral = {"ŋǁ", "ɴǁ"};
    private final String[] click = concatenate(
            clickTenuis,
            clickVoiced,
            clickNasal,
            clickTenuisLateral,
            clickVoicedLateral,
            clickNasalLateral);
    private final String[] implosiveVoiced = {"ɓ", "ɗ", "ᶑ", "ʄ", "ɠ", "ʛ"};
    private final String[] implosiveVoiceless = {"ɓ̥", "ɗ̥̥", "ᶑ̊", "ʄ̊", "ɠ̊", "ʛ̥"};
    private final String[] implosive = concatenate(
            implosiveVoiced,
            implosiveVoiceless);
    // Affricates
    // Pulmonic
    private final String[] affricatePulmonicSibilant = {"ʦ", "ʣ", "ʧ", "ʤ", "ʈ͡ʂ", "ɖ͡ʐ", "ʨ", "ʥ"};
    private final String[] affricatePulmonicNonSibilant = {"p͡ɸ", "b͡β", "p̪͡f", "b̪͡v", "t̪͡θ", "d̪͡ð", "t͡ɹ̝̊", "d͡ɹ̝̠", "t̠͡ɹ̠̊˔", "d̠͡ɹ̠˔", "c͡ç", "ɟ͡ʝ", "k͡x", "ɡ͡ɣ", "q͡χ", "ɢ͡ʁ", "ʡ͡ʢ", "ʔ͡h"};
    private final String[] affricatePulmonicLateral = {"t͡ɬ", "d͡ɮ", "ʈ͡ɭ̊˔", "ɖ͡ɭ˔", "c͡ʎ̝̊", "ɟ͡ʎ̝", "k͡ʟ̝̊", "ɡ͡ʟ̝"};
    private final String[] affricatePulmonic = concatenate(
            affricatePulmonicSibilant,
            affricatePulmonicNonSibilant,
            affricatePulmonicLateral);
    private final String[] affricateEjectiveCentral = {"t̪͡θʼ", "t͡sʼ", "t̠͡ʃʼ", "ʈ͡ʂʼ", "k͡xʼ", "q͡χʼ"};
    private final String[] affricateEjectiveLateral = {"t͡ɬʼ", "c͡ʎ̝̊ʼ", "k͡ʟ̝̊ʼ"};
    private final String[] affricateEjective = concatenate(
            affricateEjectiveCentral,
            affricateEjectiveLateral);
    private final String[] affricate = concatenate(
            affricatePulmonic,
            affricateEjective);
    //Co-articulated consonants
    private final String[] coArticulatedNasal = {"n͡m", "ŋ͡m"};
    private final String[] coArticulatedPlosive = {"t͡p", "d͡b", "k͡p", "ɡ͡b", "q͡ʡ"};
    private final String[] coArticulatedFricativeApproximant = {"ɥ̊", "ɥ", "ʍ", "w", "ɧ"};
    private final String[] coArticulatedLateral = {"ɫ"};
    private final String[] coArticulated = concatenate(
            coArticulatedNasal,
            coArticulatedPlosive,
            coArticulatedFricativeApproximant,
            coArticulatedLateral);
    // All consonants
    private final String[] consonants = concatenate(
            PlumonicConsonants,
            ejective,
            click,
            implosive,
            affricate,
            coArticulated);
    ////////////////////////////////////////////////////////////////////////////////////////
    //////////////                 VOWELS
    ////////////////////////////////////////////////////////////////////////////////////////
    private final String[] vowelClose = {"i", "y", "ɨ", "ʉ", "ɯ", "u"};
    private final String[] vowelNearClose = {"ɪ", "ʏ", "ʊ"};
    private final String[] vowelCloseMid = {"e", "ø", "ɘ", "ɵ", "ɤ", "o"};
    private final String[] vowelMid = {"e̞", "ø̞", "ə", "ɤ̞", "o̞"};
    private final String[] vowelOpenMid = {"ɛ", "œ", "ɜ", "ɞ", "ʌ", "ɔ"};
    private final String[] vowelNearOpen = {"æ", "ɐ"};
    private final String[] vowelOpen = {"a", "ɶ", "ä", "ɑ", "ɒ"};
    private final String[] vowel = concatenate(
            vowelClose,
            vowelNearClose,
            vowelCloseMid,
            vowelMid,
            vowelOpenMid,
            vowelNearOpen,
            vowelOpen);
    ////////////////////////////////////////////////////////////////////////////////////////
    private final String[] allSounds = concatenate(consonants, vowel);

    ///
    // Additions
    private final String[] constanantAddition = {"ʰ", "ʷ", "ʲ", "ʷʰ","ː"};
    private final String[] vowelAddition = {"ː", "ʼ"};

    @Autowired
    public IPAService() {
    }

    public static String[] concatenate(String[]... arrays) {
        return Stream.of(arrays)
                .flatMap(Stream::of)        // or, use `Arrays::stream`
                .distinct()
                .filter(w -> !w.isBlank())
                .sorted((o1, o2) -> o2.length() - o1.length())
                .toArray(String[]::new);
    }

    public String cleanIPA(String ipaString) {
//        IPA = IPA.normalize('NFD');
        return ipaString
                .trim()
                .replaceAll("g", "ɡ")
                .replaceAll(":", "ː")
                .replaceAll("’", "ʼ")
                .replaceAll("Ø", "∅")
                .replaceAll("ɚ", "ə˞");
//                .replaceAll("\\?", "ʔ");
//                .replaceAll('!VOICELESS PALATAL FRICATIVE', 'ç');
    }

    public String[] getPlumonicConsonantsNasal() {
        return plumonicConsonantsNasal;
    }

    public String[] getPlumonicConsonantsPlosive() {
        return plumonicConsonantsPlosive;
    }

    public String[] getPlumonicConsonantsSibilantFricative() {
        return plumonicConsonantsSibilantFricative;
    }

    public String[] getPlumonicConsonantsNonSibilantFricative() {
        return plumonicConsonantsNonSibilantFricative;
    }

    public String[] getPlumonicConsonantsApproximant() {
        return plumonicConsonantsApproximant;
    }

    public String[] getPlumonicConsonantsTapFlap() {
        return plumonicConsonantsTapFlap;
    }

    public String[] getPlumonicConsonantsTrill() {
        return plumonicConsonantsTrill;
    }

    public String[] getPlumonicConsonantsLateralFricative() {
        return plumonicConsonantsLateralFricative;
    }

    public String[] getPlumonicConsonantsLateralApproximant() {
        return plumonicConsonantsLateralApproximant;
    }

    public String[] getPlumonicConsonantsLateralTapFlap() {
        return plumonicConsonantsLateralTapFlap;
    }

    public String[] getPlumonicConsonants() {
        return PlumonicConsonants;
    }

    public String[] getEjectiveStop() {
        return ejectiveStop;
    }

    public String[] getEjectiveFricative() {
        return ejectiveFricative;
    }

    public String[] getEjectiveStopLateralFricative() {
        return ejectiveLateralFricative;
    }

    public String[] getEjective() {
        return ejective;
    }

    public String[] getClickTenuis() {
        return clickTenuis;
    }

    public String[] getClickVoiced() {
        return clickVoiced;
    }

    public String[] getClickNasal() {
        return clickNasal;
    }

    public String[] getClickTenuisLateral() {
        return clickTenuisLateral;
    }

    public String[] getClickVoicedLateral() {
        return clickVoicedLateral;
    }

    public String[] getClickNasalLateral() {
        return clickNasalLateral;
    }

    public String[] getClick() {
        return click;
    }

    public String[] getImplosiveVoiced() {
        return implosiveVoiced;
    }

    public String[] getImplosiveVoiceless() {
        return implosiveVoiceless;
    }

    public String[] getImplosive() {
        return implosive;
    }

    public String[] getAffricatePulmonicSibilant() {
        return affricatePulmonicSibilant;
    }

    public String[] getAffricatePulmonicNonSibilant() {
        return affricatePulmonicNonSibilant;
    }

    public String[] getAffricatePulmonicLateral() {
        return affricatePulmonicLateral;
    }

    public String[] getAffricatePulmonic() {
        return affricatePulmonic;
    }

    public String[] getAffricateEjectiveCentral() {
        return affricateEjectiveCentral;
    }

    public String[] getAffricateEjectiveLateral() {
        return affricateEjectiveLateral;
    }

    public String[] getAffricateEjective() {
        return affricateEjective;
    }

    public String[] getAffricate() {
        return affricate;
    }

    public String[] getConsonants() {
        return consonants;
    }

    public String[] getCoArticulatedNasal() {
        return coArticulatedNasal;
    }

    public String[] getCoArticulatedPlosive() {
        return coArticulatedPlosive;
    }

    public String[] getCoArticulatedFricativeApproximant() {
        return coArticulatedFricativeApproximant;
    }

    public String[] getCoArticulatedLateral() {
        return coArticulatedLateral;
    }

    public String[] getCoArticulated() {
        return coArticulated;
    }

    public String[] getVowelClose() {
        return vowelClose;
    }

    public String[] getVowelNearClose() {
        return vowelNearClose;
    }

    public String[] getVowelCloseMid() {
        return vowelCloseMid;
    }

    public String[] getVowelMid() {
        return vowelMid;
    }

    public String[] getVowelOpenMid() {
        return vowelOpenMid;
    }

    public String[] getVowelNearOpen() {
        return vowelNearOpen;
    }

    public String[] getVowelOpen() {
        return vowelOpen;
    }

    public String[] getVowel() {
        return vowel;
    }

    public String[] getAllSounds() {
        return allSounds;
    }

    public String[] getAllConstanantVariants() {
        return getVariables(constanantAddition, consonants);
    }

    public String[] getAllVowelVariants() {
        return getVariables(vowelAddition, vowel);
    }

    public String[] getAllSoundsWithVariants() {
        return concatenate(getAllVowelVariants(), getAllConstanantVariants());
    }

    private String[] getVariables(String[] addition, String[] phonemes) {
        String[] variables = Arrays.stream(addition)
                .map((s) -> Arrays.stream(phonemes)
                        .map((c) -> c + s)
                        .collect(Collectors.toList()))
                .reduce((l1, l2) -> Stream.concat(l1.stream(), l2.stream()).collect(Collectors.toList()))
                .map((l) -> l.toArray(new String[0]))
                .orElse(new String[0]);
        return concatenate(phonemes,
                variables);
    }
}
