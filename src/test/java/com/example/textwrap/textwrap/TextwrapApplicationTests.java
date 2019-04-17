package com.example.textwrap.textwrap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Scanner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TextwrapApplicationTests {

  @InjectMocks @Spy TextwrapApplication textwrapApplication;

  private int MAX_LENGTH = 13;

  @Test
  public void testDoParsingReturnList_Success() {
    when(textwrapApplication.getInputScanner()).thenReturn(new Scanner("Four score and"));

    List<String> result = textwrapApplication.doParsingReturnList(MAX_LENGTH);

    assertEquals(2, result.size());
  }

  @Test
  public void testDoParsingReturnList_WordOverCharacterCount() {
    when(textwrapApplication.getInputScanner())
        .thenReturn(new Scanner("Four score andsadfmnasdandmfamndmfnamndmfnamn"));

    List<String> result = textwrapApplication.doParsingReturnList(MAX_LENGTH);

    assertNull(result);
  }

  @Test
  public void testDoParsingReturnList_TwoStringsExactlyMaxLength() {
    when(textwrapApplication.getInputScanner()).thenReturn(new Scanner("brought forth upon"));

    List<String> result = textwrapApplication.doParsingReturnList(MAX_LENGTH);

    assertSame(2, result.size());
  }
}
