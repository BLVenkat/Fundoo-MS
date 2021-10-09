package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.entity.Note;

public interface NoteService {

	public Note createNote(NoteDTO noteDTO,String token);
	
	public List<Note> getNotes(String token);
	
	public Note pinNote(String token,Long noteId);
	
	public Note archiveNote(String token,Long noteId);
	
	public Note trashNote(String token,Long noteId);

	public Note deleteNote(String token,Long noteId);
	
	public Note getNote(String token,Long noteId);
}
