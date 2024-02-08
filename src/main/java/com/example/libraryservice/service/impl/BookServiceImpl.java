package com.example.libraryservice.service.impl;

import com.example.libraryservice.dto.BookDto;
import com.example.libraryservice.enums.EColumns;
import com.example.libraryservice.enums.ESortingValue;
import com.example.libraryservice.enums.ExceptionCodes;
import com.example.libraryservice.exceptions.ApiException;
import com.example.libraryservice.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.libraryservice.util.ConversionUtil.convertStringToInteger;
import static com.example.libraryservice.util.DateTimeUtils.convertStringToLocalDateTime;
import static com.example.libraryservice.util.ValidatorUtil.checkNumber;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final List<BookDto> books = new ArrayList<>();

    @Value("${file.path}")
    private String filePath;

    @PostConstruct
    public void init() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath)), StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord record : csvParser) {
                if (csvParser.getCurrentLineNumber() == 1) continue;
                BookDto book = new BookDto();
                book.setId(record.isSet(0) && !record.get(0).isEmpty() ? Long.parseLong(record.get(0)) : null);
                book.setBook(record.isSet(1) && !record.get(1).isEmpty() ? record.get(1) : null);
                book.setSeries(record.isSet(2) && !record.get(2).isEmpty() ? record.get(2) : null);
                book.setReleaseNumber(record.isSet(3) && !record.get(3).isEmpty() ? record.get(3) : null);
                book.setAuthor(record.isSet(4) && !record.get(4).isEmpty() ? record.get(4) : null);
                book.setDescription(record.isSet(5) && !record.get(5).isEmpty() ? record.get(5) : null);
                book.setNumPages(record.isSet(6) && !record.get(6).isEmpty() ? convertStringToInteger(record.get(6)) : null);
                book.setFormat(record.isSet(7) && !record.get(7).isEmpty() ? record.get(7) : null);
                book.setGenres(record.isSet(8) && !record.get(8).isEmpty() && !record.get(8).equals("[]") ? parseGenres(record.get(8)) : new ArrayList<>());
                book.setPublicationDate(convertStringToLocalDateTime(record.get(9)));
                book.setRating(record.isSet(10) && !record.get(10).isEmpty() ? checkNumber(record.get(10)) : null);
                book.setNumberOfVoters(record.isSet(11) && !record.get(11).isEmpty() ? Integer.parseInt(record.get(11)) : null);

                books.add(book);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ApiException(ExceptionCodes.SYSTEM_FAILURE, "Error during parsing of the file!");
        }
    }

    private List<String> parseGenres(String genres) {
        genres = genres.substring(1, genres.length() - 1);
        String[] genresArray = genres.split(", ");
        return Arrays.stream(genresArray)
                .map(genre -> genre.replaceAll("^'|'$", ""))
                .collect(Collectors.toList());
    }


    @Override
    public ResponseEntity<?> getFilteredBooks(Integer year, EColumns column, ESortingValue sorting) {
        try {
            List<BookDto> top10Books = books.stream()
                    .filter(book -> year == null || book.getPublicationDate() != null && book.getPublicationDate().getYear() == year)
                    .filter(book -> getValueByColumn(book, column) != null)
                    .sorted(Objects.requireNonNull(getComparator(column, sorting)))
                    .limit(10)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(top10Books);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApiException(ExceptionCodes.SYSTEM_FAILURE, "Internal server error");
        }
    }

    private Comparator<BookDto> getComparator(EColumns column, ESortingValue sorting) {
        switch (column) {
            case BOOK:
                return sorting.equals(ESortingValue.ASC)
                        ? Comparator.comparing(BookDto::getBook)
                        : Comparator.comparing(BookDto::getBook).reversed();
            case AUTHOR:
                return sorting.equals(ESortingValue.ASC)
                        ? Comparator.comparing(BookDto::getAuthor)
                        : Comparator.comparing(BookDto::getAuthor).reversed();
            case NUM_PAGES:
                return sorting.equals(ESortingValue.ASC)
                        ? Comparator.comparing(BookDto::getNumPages)
                        : Comparator.comparing(BookDto::getNumPages).reversed();
            case PUBLICATION_DATE:
                return sorting.equals(ESortingValue.ASC)
                        ? Comparator.comparing(BookDto::getPublicationDate)
                        : Comparator.comparing(BookDto::getPublicationDate).reversed();
            case RATING:
                return sorting.equals(ESortingValue.ASC)
                        ? Comparator.comparing(BookDto::getRating)
                        : Comparator.comparing(BookDto::getRating).reversed();
            case NUMBER_OF_VOTERS:
                return sorting.equals(ESortingValue.ASC)
                        ? Comparator.comparing(BookDto::getNumberOfVoters)
                        : Comparator.comparing(BookDto::getNumberOfVoters).reversed();
            default:
                return Comparator.comparing(BookDto::getNumberOfVoters);
        }
    }

    private Object getValueByColumn(BookDto book, EColumns column) {
        switch (column) {
            case BOOK:
                return book.getBook();
            case AUTHOR:
                return book.getAuthor();
            case NUM_PAGES:
                return book.getNumPages();
            case PUBLICATION_DATE:
                return book.getPublicationDate();
            case RATING:
                return book.getRating();
            case NUMBER_OF_VOTERS:
                return book.getNumberOfVoters();
            default:
                return book.getId();
        }
    }


}
