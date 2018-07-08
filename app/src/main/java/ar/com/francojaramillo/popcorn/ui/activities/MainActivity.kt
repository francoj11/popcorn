package ar.com.francojaramillo.popcorn.ui.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import ar.com.francojaramillo.popcorn.PopcornApplication
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import ar.com.francojaramillo.popcorn.R
import ar.com.francojaramillo.popcorn.ui.fragments.FavoritesMoviesFragment
import ar.com.francojaramillo.popcorn.ui.fragments.MoviesFragment
import ar.com.francojaramillo.popcorn.ui.fragments.SearchFragment
import ar.com.francojaramillo.popcorn.viewmodels.FavsMovieViewModel
import ar.com.francojaramillo.popcorn.viewmodels.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import javax.inject.Inject

/**
 * Main activity that manages the navigation in the app.
 * Is in charge of loading fragments and interchange them.
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        SearchFragment.OnMoviesListResultListener {

    // Tag  for Logging
    private val TAG = "POPCORN_TAG"

    private val FAVS_FRAGMENT_TAG = "favFragmentTag"

    private var mGoogleSignInClient: GoogleSignInClient? = null

    // Viewmodels
    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var favsMovieViewModel: FavsMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupGoogleSignIn()

        setupNavigationDrawer()

        // Dagger DI
        (application as PopcornApplication).appComponent.inject(this)

        favsMovieViewModel = ViewModelProviders.of(this, viewModelFactory)
                                                    .get(FavsMovieViewModel::class.java)

        loadFragment(SearchFragment.newInstance(), null)
    }

    /**
     * setup the navigation drawer
     */
    fun setupNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    /**
     * Loads a new fragment into the fragment container
     */
    fun loadFragment(fragment: Fragment, fragmentTag: String?) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, fragmentTag)
                .commit()

    }

    /**
     * Setups the GoogleClient for signing in
     */
    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    /**
     * Replaces the current fragment in the fragment container with the new one
     * TODO: Implement animation
     */
    fun replaceFragment(fragment: Fragment, fragmentTag: String?, addToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTag)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragmentTag)
        }

        fragmentTransaction.commit()
    }

    /**
     * Manages the back button logic:
     *      - If navdrawer is open -> close it
     *      - If seeing the favs and press back -> go back to search fragment
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            // Check which fragment is displayed
            if (supportFragmentManager.backStackEntryCount > 0) {
                val index = supportFragmentManager.backStackEntryCount - 1
                val backEntry = supportFragmentManager.getBackStackEntryAt(index)
                if (backEntry.name ==  FAVS_FRAGMENT_TAG) {
                    while (supportFragmentManager.backStackEntryCount > 1) {
                        supportFragmentManager.popBackStackImmediate()
                    }
                }
            }
            super.onBackPressed()
        }
    }

    /**
     * Adds menu items to the menu bar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /**
     * Manages the Menu item selection
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_favs -> {
                replaceFragment(FavoritesMoviesFragment.newInstance(), FAVS_FRAGMENT_TAG, true)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * Manages the Navigation Drawer item selection
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.action_favorites -> {
                replaceFragment(FavoritesMoviesFragment.newInstance(), FAVS_FRAGMENT_TAG, true)
            }
            R.id.action_log_out -> { signOutCleanUp() }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Signs out and cleans the favorites
     */
    fun signOutCleanUp() {
        mGoogleSignInClient?.signOut()

        favsMovieViewModel.deleteAllMovies()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * When there is a  result, it changes the search fragment for the Movies Fragment that
     * displays the result in a nice list
     */
    override fun onMoviesListResult() {
        replaceFragment(MoviesFragment.newInstance(), null, true)
    }
}
