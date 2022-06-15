package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.views.View;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("/books")
public class BookController {

    private final MessageSource messageSource;

    public BookController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/stock/{isbn}")
    public Integer stock(String isbn) {
        throw new OUtOfStockException();
    }

    @View("bookscreate")
    @Get("/create")
    public Map<String, Object> create() {

        return createModelWithBlankValues();
    }

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post("/save")
    public HttpResponse save(@Valid @Body CommandBookSave cmd) {
        return HttpResponse.ok();
    }

    @View("bookscreate")
    @Error(exception = ConstraintViolationException.class)
    public Map<String, Object> onSavedFailed(HttpRequest request, ConstraintViolationException ex) {
        final Map<String, Object> model = createModelWithBlankValues();
        model.put("errors", messageSource.violationsMessages(ex.getConstraintViolations()));
        Optional<CommandBookSave> cmd = request.getBody(CommandBookSave.class);
        cmd.ifPresent(bookSave -> populateModel(model, bookSave));
        return model;
    }

    private Map<String, Object> createModelWithBlankValues() {

        final Map<String, Object> model = new HashMap<>();
        model.put("title", "");
        model.put("pages", "");
        return model;
    }

    private void populateModel(Map<String, Object> model, CommandBookSave bookSave) {
        model.put("title", bookSave.getTitle());
        model.put("pages", bookSave.getPages());
    }

}
