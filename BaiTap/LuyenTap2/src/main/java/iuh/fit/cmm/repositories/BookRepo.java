/*
 * @ (#) BookRepo.java   11/1/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.cmm.repositories;

import iuh.fit.cmm.models.Book;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 11/1/2026
 * @version 1.0
 */
@Repository
public interface BookRepo extends ListCrudRepository<Book, Integer> {
    List<Book> findByAuthorContaining(String author);
    List<Book> findByPublisherIdAndAuthorContaining(int publisherId, String author);
}
