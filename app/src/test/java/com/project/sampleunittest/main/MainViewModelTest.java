package com.project.sampleunittest.main;

import static com.project.sampleunittest.main.MainViewModel.GET_LIST_POPULAR_DONE;
import static com.project.sampleunittest.main.MainViewModel.GET_LIST_POPULAR_FAILURE;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import android.arch.lifecycle.MutableLiveData;
import com.project.sampleunittest.data.BaseResponse;
import com.project.sampleunittest.data.FetchDataStatus;
import com.project.sampleunittest.data.ListMovieResponse;
import com.project.sampleunittest.data.MovieRepository;
import com.project.sampleunittest.data.MovieResponse;
import com.project.sampleunittest.detail.DetailViewModel;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EventBus.class})
public class MainViewModelTest {
    @Mock
    private MovieRepository mMovieRepository;

    @Mock
    private MutableLiveData<Boolean> loadDataStatus;

    @Mock
    private EventBus mEventBus;

    @Mock
    private List<MovieResponse> mList;

    @Spy
    private MainViewModel mMainViewModel = new MainViewModel(mMovieRepository);

    @BeforeClass
    public static void setUpClass() {

        /**
         * This using for test RxJava
         * the default scheduler returned by AndroidSchedulers.mainThread() is an instance of
         * LooperScheduler and relies on Android dependencies that are not available in JUnit tests.
         * To solve this problem we need to initialize RxAndroidPlugins with a different Scheduler before the tests are run
         *
         * If you run each test method seperately u will not need this setupClass method
         */
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
    public void test_getListPopular_success(){
        mockStatic(EventBus.class);
        ListMovieResponse listMovieResponse = new ListMovieResponse();
        listMovieResponse.setStatus_code(BaseResponse.STATUS_CODE_SUCCESS);
        mMainViewModel.setMovieRepository(mMovieRepository);
        mMainViewModel.setList(mList);

        Mockito.when(mMovieRepository.getListPopular()).thenReturn(Single.just(listMovieResponse));
        Mockito.when(EventBus.getDefault()).thenReturn(mEventBus);
        Mockito.when(mMainViewModel.getLoadDataStatus()).thenReturn(loadDataStatus);

        mMainViewModel.getListPopular();

        Mockito.verify(loadDataStatus).postValue(true);
        Mockito.verify(loadDataStatus).postValue(false);
        Mockito.verify(mEventBus).post(new FetchDataStatus(GET_LIST_POPULAR_DONE));
        Mockito.verify(mList).addAll(any());
        Mockito.verifyNoMoreInteractions(mEventBus);
    }

    @Test
    public void test_getListPopular_failure(){
        mockStatic(EventBus.class);
        int statusCodeFail = 90;
        ListMovieResponse listMovieResponse = new ListMovieResponse();
        listMovieResponse.setStatus_code(statusCodeFail);
        mMainViewModel.setMovieRepository(mMovieRepository);
        mMainViewModel.setList(mList);

        Mockito.when(mMovieRepository.getListPopular()).thenReturn(Single.just(listMovieResponse));
        Mockito.when(EventBus.getDefault()).thenReturn(mEventBus);
        Mockito.when(mMainViewModel.getLoadDataStatus()).thenReturn(loadDataStatus);

        mMainViewModel.getListPopular();

        Mockito.verify(loadDataStatus).postValue(true);
        Mockito.verify(loadDataStatus).postValue(false);
        Mockito.verify(mEventBus).post(new FetchDataStatus(GET_LIST_POPULAR_FAILURE));
        Mockito.verify(mEventBus, times(0)).post(new FetchDataStatus(GET_LIST_POPULAR_DONE));
        Mockito.verifyZeroInteractions(mList);
    }

    @Test
    public void test_getDetailMovie_throwException() {
        mockStatic(EventBus.class);
        mMainViewModel.setMovieRepository(mMovieRepository);

        Mockito.when(mMovieRepository.getListPopular()).thenReturn(Single.error(new RuntimeException()));
        Mockito.when(EventBus.getDefault()).thenReturn(mEventBus);
        Mockito.when(mMainViewModel.getLoadDataStatus()).thenReturn(loadDataStatus);

        mMainViewModel.getListPopular();

        Mockito.verify(loadDataStatus).postValue(true);
        Mockito.verify(loadDataStatus).postValue(false);
        Mockito.verifyZeroInteractions(mList);
        Mockito.verifyZeroInteractions(mEventBus);
        Mockito.verify(mMainViewModel).handleError(any(RuntimeException.class));
    }
}