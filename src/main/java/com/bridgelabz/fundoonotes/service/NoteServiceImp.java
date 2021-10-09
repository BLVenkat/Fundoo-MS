package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.FundooException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utils.TokenService;

@Service
public class NoteServiceImp implements NoteService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Override
	public Note createNote(NoteDTO noteDTO, String token) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		Note note = new Note();
		BeanUtils.copyProperties(noteDTO, note);
		note = noteRepository.save(note);
		user.getNotes().add(note);
		userRepository.save(user);
		return note;
	}

	@Override
	public List<Note> getNotes(String token) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		return user.getNotes().stream().filter(note -> note.getIsTrashed().equals(false)).collect(Collectors.toList());
	}

	@Override
	public Note pinNote(String token, Long noteId) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		
		Note isNotePresent = user.getNotes().stream().filter(note -> note.getId().equals(noteId)).findAny()
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "Note not found"));
		
		if(isNotePresent.getIsPinned().equals(true)) {
			isNotePresent.setIsPinned(false);
		}
		else {
			isNotePresent.setIsPinned(true);
		}
		return noteRepository.save(isNotePresent);
	}

	@Override
	public Note archiveNote(String token, Long noteId) {
		
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		
		Note isNotePresent = user.getNotes().stream().filter(note -> note.getId().equals(noteId)).findAny()
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "Note not found"));
		
		if(isNotePresent.getIsArchived().equals(true)) {
			isNotePresent.setIsArchived(false);
		}
		else {
			isNotePresent.setIsArchived(true);
		}
		return noteRepository.save(isNotePresent);

	}

	@Override
	public Note trashNote(String token, Long noteId) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		
		Note isNotePresent = user.getNotes().stream().filter(note -> note.getId().equals(noteId)).findAny()
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "Note not found"));
		
		if(isNotePresent.getIsTrashed().equals(true)) {
			isNotePresent.setIsTrashed(false);
		}
		else {
			isNotePresent.setIsTrashed(true);
		}
		return noteRepository.save(isNotePresent);

	}

	@Override
	public Note deleteNote(String token, Long noteId) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		
		Note isNotePresent = user.getNotes().stream().filter(note -> note.getId().equals(noteId)).findAny()
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "Note not found"));
		noteRepository.delete(isNotePresent);
		return isNotePresent;
	}

	@Override
	public Note getNote(String token, Long noteId) {
		User user = userRepository.findById(tokenService.decodeToken(token))
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		
		 return user.getNotes().stream().filter(note -> note.getId().equals(noteId)).findAny()
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "Note not found"));
	}

}
