package toff.novi.eindopdrachttoffshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toff.novi.eindopdrachttoffshop.models.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByIsReadFalse();

    List<Contact> findByIsReadTrue();

    List<Contact> findByEmail(String email);

    List<Contact> findBySubjectContainingIgnoreCase(String subject);

    long countByIsReadFalse();
}
