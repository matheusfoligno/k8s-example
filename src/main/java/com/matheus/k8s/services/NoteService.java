package com.matheus.k8s.services;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.matheus.k8s.configs.KnotePropertiesConfig;
import com.matheus.k8s.entities.NoteEntity;
import com.matheus.k8s.repositories.NoteRepository;

@Service
public class NoteService {

	private Parser parser = Parser.builder().build();
	private HtmlRenderer renderer = HtmlRenderer.builder().build();

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private KnotePropertiesConfig properties;

	public void saveNote(String description, Model model) {
		if (description != null && !description.trim().isEmpty()) {
			Node document = parser.parse(description.trim());
			String html = renderer.render(document);
			noteRepository.save(new NoteEntity(null, html));
			model.addAttribute("description", "");
		}
	}

	public void getAllNotes(Model model) {
		List<NoteEntity> notes = noteRepository.findAll();
		Collections.reverse(notes);
		model.addAttribute("notes", notes);
	}

	public void uploadImage(MultipartFile file, String description, Model model) throws Exception {
		File uploadsDir = new File(properties.getUploadDir());
		if (!uploadsDir.exists()) {
			uploadsDir.mkdir();
		}
		String fileId = UUID.randomUUID().toString() + "." + file.getOriginalFilename().split("\\.")[1];
		file.transferTo(new File(properties.getUploadDir() + fileId));
		model.addAttribute("description", description + " ![](/uploads/" + fileId + ")");
	}
}
