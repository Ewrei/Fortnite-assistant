package robin.vitalij.fortniteassitant.di.module

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import robin.vitalij.fortniteassitant.utils.view.ResourceProvider
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun resourceProvider(context: Context) = ResourceProvider(context)

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
}