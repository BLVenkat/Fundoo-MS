package com.bridgelabz.fundoonotes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.NoteImage;

public interface NoteService {

	public Note createNote(NoteDTO noteDTO,String token);
	
	public List<Note> getNotes(String token);
	
	public Note pinNote(String token,Long noteId);
	
	public Note archiveNote(String token,Long noteId);
	
	public Note trashNote(String token,Long noteId);

	public Note deleteNote(String token,Long noteId);
	
	public Note getNote(String token,Long noteId);
	
	public NoteImage addImage(String token,MultipartFile file,Long noteId);
}
