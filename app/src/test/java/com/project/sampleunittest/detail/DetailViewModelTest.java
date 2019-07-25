package com.project.sampleunittest.detail;

import static com.project.sampleunittest.detail.DetailViewModel.GET_DETAIL_MOVIE_FAILURE;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import android.arch.lifecycle.MutableLiveData;
import com.project.sampleunittest.data.FetchDataStatus;
import com.project.sampleunittest.data.MovieRepository;
import com.project.sampleunittest.data.MovieResponse;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.greenrobot.eventbus.EventBus;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EventBus.class})
public class DetailViewModelTest {

    @Mock
    private MovieRepository mMovieRepository;

    @Mock
    private MutableLiveData<Boolean> loadDataStatus;

    @Mock
    private EventBus mEventBus;

    @Spy
    private DetailViewModel mDetailViewModel = new DetailViewModel(mMovieRepository);

    @BeforeClass
    public static void setUpClass() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, false);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @AfterClass
    public static void tearDownClass() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getDetailMovie_success() {
        mockStatic(EventBus.class);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setStatus_code(MovieResponse.STATUS_CODE_SUCCESS);
        FetchDataStatus status = new FetchDataStatus(GET_DETAIL_MOVIE_FAILURE);
        mDetailViewModel.setMovieRepository(mMovieRepository);

        Mockito.when(mMovieRepository.getDetail(anyInt())).thenReturn(Single.just(movieResponse));
        Mockito.when(mDetailViewModel.getLoadDataStatus()).thenReturn(loadDataStatus);
        Mockito.when(EventBus.getDefault()).thenReturn(mEventBus);

        mDetailViewModel.getDetailMovie(131);

        Mockito.verify(loadDataStatus).postValue(true);
        Mockito.verify(loadDataStatus).postValue(false);
        Mockito.verify(mDetailViewModel).setMovie(movieResponse);
        Mockito.verifyZeroInteractions(mEventBus);
//        Mockito.verify(mEventBus, times(0)).post(status);
    }

    @Test
    public void test_getDetailMovie_failure() {
        mockStatic(EventBus.class);
        int statusCodeFail = 39;
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setStatus_code(statusCodeFail);
        FetchDataStatus status = new FetchDataStatus(GET_DETAIL_MOVIE_FAILURE);
        mDetailViewModel.setMovieRepository(mMovieRepository);

        Mockito.when(mMovieRepository.getDetail(anyInt())).thenReturn(Single.just(movieResponse));
        Mockito.when(mDetailViewModel.getLoadDataStatus()).thenReturn(loadDataStatus);
        Mockito.when(EventBus.getDefault()).thenReturn(mEventBus);

        mDetailViewModel.getDetailMovie(131);

        Mockito.verify(loadDataStatus).postValue(true);
        Mockito.verify(loadDataStatus).postValue(false);
        Mockito.verify(mEventBus).post(status);
        Mockito.verify(mDetailViewModel, times(0)).setMovie(movieResponse);
    }

    @Test
    public void test_getDetailMovie_throwException() {
        mockStatic(EventBus.class);
        mDetailViewModel.setMovieRepository(mMovieRepository);

        Mockito.when(mMovieRepository.getDetail(anyInt())).thenReturn(Single.error(new RuntimeException()));
        Mockito.when(mDetailViewModel.getLoadDataStatus()).thenReturn(loadDataStatus);
        Mockito.when(EventBus.getDefault()).thenReturn(mEventBus);

        mDetailViewModel.getDetailMovie(131);

        Mockito.verify(loadDataStatus).postValue(true);
        Mockito.verify(loadDataStatus).postValue(false);
        Mockito.verify(mDetailViewModel).handleError(any(Throwable.class));
        Mockito.verify(mEventBus, times(0)).post(any(FetchDataStatus.class));
        Mockito.verify(mDetailViewModel, times(0)).setMovie(any(MovieResponse.class));
    }
}