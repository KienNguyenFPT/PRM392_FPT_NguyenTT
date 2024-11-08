package com.example.bookauthormanagement.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookauthormanagement.dao.AuthorDao;
import com.example.bookauthormanagement.models.Author;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthorViewModel extends AndroidViewModel {
    private AuthorDao authorDao;
    private MutableLiveData<List<Author>> authorsLiveData;
    private ExecutorService executorService;

    public AuthorViewModel(@NonNull Application application) {
        super(application);
        authorDao = new AuthorDao(application);
        authorsLiveData = new MutableLiveData<>();
        executorService = Executors.newFixedThreadPool(2);
    }


    public LiveData<List<Author>> getAuthors() {
        return authorsLiveData;
    }

    public void loadAuthors() {
        List<Author> authors = authorDao.getAllAuthors();
        authorsLiveData.setValue(authors);
    }

    public void insertAuthor(Author author) {
        authorDao.insertAuthor(author);
        loadAuthors();
    }

    public void updateAuthor(Author author) {
        authorDao.updateAuthor(author);
        loadAuthors();
    }

    public void deleteAuthor(int authorId) {
        authorDao.deleteAuthor(authorId);
        loadAuthors();
    }

    public LiveData<Author> getAuthorById(int authorId) {
        MutableLiveData<Author> author = new MutableLiveData<>();
        executorService.execute(() -> {
            Author fetchedAuthor = authorDao.getAuthorById(authorId);
            author.postValue(fetchedAuthor);
        });
        return author;
    }

}
