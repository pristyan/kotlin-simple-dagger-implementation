package id.cocoba.offlinestore.component

import dagger.Component
import id.cocoba.offlinestore.MainActivity

/**
 * Created by Chandra on 26/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

@Component
interface DummyInjector {
    fun inject(act: MainActivity)
}