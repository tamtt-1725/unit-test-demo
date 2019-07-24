package com.project.sampleunittest.data;

import static org.junit.Assert.*;

import android.content.Context;
import com.project.sampleunittest.InjectRepository;
import com.project.sampleunittest.network.MovieDBService;
import io.reactivex.observers.TestObserver;
import org.junit.*;
import org.mockito.*;
import retrofit2.HttpException;

public class MovieRepositoryTest {

    @Mock
    private Context mContext;

    private MovieRepository mMovieRepository;

    private TestObserver<ListMovieResponse> mListMovieResponseTestObserver;

    private TestObserver<MovieResponse> mMovieResponseTestObserver;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mMovieRepository = InjectRepository.provideRepository(mContext);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_getDetail_success(){
        mMovieResponseTestObserver = new TestObserver<>();

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(429617);

        mMovieRepository.getDetail(429617).subscribe(mMovieResponseTestObserver);

        mMovieResponseTestObserver.assertNoErrors();
        mMovieResponseTestObserver.assertComplete();
        mMovieResponseTestObserver.assertValue(movieResponse);
    }

    @Test
    public void test_getDetail_fail(){
        mMovieResponseTestObserver = new TestObserver<>();

        mMovieRepository.getDetail(7).subscribe(mMovieResponseTestObserver);

        mMovieResponseTestObserver.assertError(HttpException.class);
    }

    @Test
    public void test_getListPopular_success(){
        mListMovieResponseTestObserver = new TestObserver<>();

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(429617);

        mMovieRepository.getListPopular().subscribe(mListMovieResponseTestObserver);

        mListMovieResponseTestObserver.assertNoErrors();
        mListMovieResponseTestObserver.assertComplete();
    }

}