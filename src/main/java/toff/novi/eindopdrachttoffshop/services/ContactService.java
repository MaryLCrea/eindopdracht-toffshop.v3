package toff.novi.eindopdrachttoffshop.services;

import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.ContactRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.ContactResponseDto;
import toff.novi.eindopdrachttoffshop.exceptions.ResourceNotFoundException;
import toff.novi.eindopdrachttoffshop.models.Contact;
import toff.novi.eindopdrachttoffshop.repositories.ContactRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactResponseDto createContact(ContactRequestDto contactRequestDto) {
        Contact contact = new Contact(
                contactRequestDto.getName(),
                contactRequestDto.getEmail(),
                contactRequestDto.getSubject(),
                contactRequestDto.getMessage()
        );

        Contact savedContact = contactRepository.save(contact);
        return new ContactResponseDto(savedContact);
    }

    public ContactResponseDto getContactById(Integer id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id " + id + " not found"));
        return new ContactResponseDto(contact);
    }

    public List<ContactResponseDto> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(ContactResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ContactResponseDto> getUnreadContacts() {
        return contactRepository.findByIsReadFalse()
                .stream()
                .map(ContactResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ContactResponseDto> getReadContacts() {
        return contactRepository.findByIsReadTrue()
                .stream()
                .map(ContactResponseDto::new)
                .collect(Collectors.toList());
    }

    public ContactResponseDto markAsRead(Integer id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id " + id + " not found"));

        contact.setIsRead(true);
        Contact updatedContact = contactRepository.save(contact);
        return new ContactResponseDto(updatedContact);
    }

    public ContactResponseDto markAsUnread(Integer id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with id " + id + " not found"));

        contact.setIsRead(false);
        Contact updatedContact = contactRepository.save(contact);
        return new ContactResponseDto(updatedContact);
    }

    public List<ContactResponseDto> searchBySubject(String subject) {
        return contactRepository.findBySubjectContainingIgnoreCase(subject)
                .stream()
                .map(ContactResponseDto::new)
                .collect(Collectors.toList());
    }

    public long getUnreadCount() {
        return contactRepository.countByIsReadFalse();
    }

    public void deleteContact(Integer id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact with id " + id + " not found");
        }
        contactRepository.deleteById(id);
    }
}
