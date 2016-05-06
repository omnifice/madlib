package com.thedogdidit.madlib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({MadlibTest.class,
        HelpExceptionTest.class,
        CmdLineParserTest.class,
        WordsReaderTest.class,
        PhrasesReaderTest.class,
        PhraseParserTest.class})

public class MadlibTestSuite {
}
