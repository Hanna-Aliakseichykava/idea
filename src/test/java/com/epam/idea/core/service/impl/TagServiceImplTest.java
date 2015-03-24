package com.epam.idea.core.service.impl;


import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.builders.TestTagBuilder;
import com.epam.idea.core.repository.TagRepository;
import com.epam.idea.core.service.TagService;
import com.epam.idea.core.service.exception.TagDoesNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Created by Ihar_Niakhlebau on 23-Mar-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagServiceImplTest {

    @Mock
    private TagRepository tagRepositoryMock;

    @InjectMocks
    private TagService sut = new TagServiceImpl();

    @Before
    public void setUp() throws Exception {
        Mockito.reset(tagRepositoryMock);
    }

    @Test
    public void shouldSaveNewIdea() throws Exception {
        //Given:
        Tag tagToSave = TestTagBuilder.aTag().build();

        //When:
        sut.save(tagToSave);

        //Then:
        ArgumentCaptor<Tag> tagCaptor = ArgumentCaptor.forClass(Tag.class);
        verify(tagRepositoryMock, times(1)).save(tagCaptor.capture());
        verifyNoMoreInteractions(tagRepositoryMock);

        Tag tagArgument = tagCaptor.getValue();
        System.out.println("!!!!!!!!!!!!!!!!!!!" + tagCaptor.getValue());
        assertThat(tagArgument.getName()).isEqualTo(tagToSave.getName());
    }

    @Test
    public void shouldFindOne() throws Exception {
        //Given:
        Tag foundTag = TestTagBuilder.aTag().build();
        when(tagRepositoryMock.findOne(eq(foundTag.getId()))).thenReturn(Optional.of(foundTag));

        //When:
        Tag actualTag = sut.findOne(foundTag.getId());

        //Then:
        assertThat(actualTag).isEqualTo(foundTag);
        verify(tagRepositoryMock, times(1)).findOne(foundTag.getId());
        verifyNoMoreInteractions(tagRepositoryMock);
    }

    @Test
    public void shouldThrowExceptionWhenTryFindTagWhichDoesNotExist() throws Exception {
        //Given:
        long tagId = 3L;
        when(tagRepositoryMock.findOne(eq(tagId))).thenReturn(Optional.empty());

        //When:
        try {
            sut.findOne(tagId);
            fail("TagDoesNotExistException expected because we try to find the tag which does not exist");
        } catch (TagDoesNotExistException e) {

            //Then:
            verify(tagRepositoryMock, times(1)).findOne(tagId);
            verifyNoMoreInteractions(tagRepositoryMock);
        }
    }


    @Test
    public void shouldReturnFindAllTags() throws Exception {
        //Given:
        List<Tag> foundTags = asList(TestTagBuilder.aTag().build());
        when(tagRepositoryMock.findAll()).thenReturn(foundTags);

        //When:
        List<Tag> actualTags = sut.findAll();

        //Then:
        ArgumentCaptor<Tag> tagCaptor = ArgumentCaptor.forClass(Tag.class);
        assertThat(actualTags).isEqualTo(foundTags);
        verify(tagRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(tagRepositoryMock);
    }

    @Test
    public void shouldDeleteNewIdea() throws Exception {
        //Given:
        Tag deletedTag = TestTagBuilder.aTag().build();
        when(tagRepositoryMock.findOne(eq(deletedTag.getId()))).thenReturn(Optional.of(deletedTag));

        //When:
        sut.delete(deletedTag);

        //Then:
        ArgumentCaptor<Tag> tagCaptor = ArgumentCaptor.forClass(Tag.class);
        verify(tagRepositoryMock, times(1)).delete(tagCaptor.capture());
        verifyNoMoreInteractions(tagRepositoryMock);

        Tag tagArgument = tagCaptor.getValue();
        assertThat(tagArgument.getName()).isEqualTo(deletedTag.getName());
    }
}
