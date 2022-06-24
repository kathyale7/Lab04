package com.example.uiexamples


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uiexamples.ui.gallery.GalleryFragment
import com.example.uiexamples.ui.home.HomeFragment
import com.example.uiexamples.ui.slideshow.SlideshowFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MenuExampleAdministrator : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var homeFragment: HomeFragment
    private lateinit var galleryFragment: GalleryFragment
    private lateinit var slideshowFragment: SlideshowFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_example_administrator)

        val bundle = intent.extras


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Toast.makeText(this, "Dentro del botón flotante", Toast.LENGTH_SHORT).show()
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_example, menu)
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.nav_home -> {
                homeFragment = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.nav_host_fragment, homeFragment)
                    .addToBackStack(null)
                    .commit()

            }
            R.id.nav_gallery -> {
                galleryFragment = GalleryFragment()
                supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.nav_host_fragment, galleryFragment)
                    .addToBackStack(null)
                    .commit()

            }
            R.id.nav_slideshow -> {
                slideshowFragment = SlideshowFragment()
                supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.nav_host_fragment, slideshowFragment)
                    .addToBackStack(null)
                    .commit()
            }




            R.id.nav_logout -> {
                val i = Intent(this, LoginExample::class.java)
                finish()
                startActivity(i)
            }
            R.id.nav_oferta -> {
                val i = Intent(this, OfertaAcademica::class.java)
                startActivity(i)
            }

            R.id.nav_matricula -> {
                val i = Intent(this, Matricula::class.java)
                startActivity(i)
            }
            R.id.nav_cursos -> {
                val i = Intent(this, CrudCursos::class.java)
                startActivity(i)
            }
            R.id.nav_carrera -> {
                val i = Intent(this, CrudCarreras::class.java)
                startActivity(i)
            }
            R.id.nav_profesores -> {
                val i = Intent(this, CrudProfesores::class.java)
                startActivity(i)
            }
            R.id.nav_alumnos -> {
                val i = Intent(this, CrudAlumnos::class.java)
                startActivity(i)
            }
            R.id.nav_ciclos -> {
                val i = Intent(this, CrudCiclos::class.java)
                startActivity(i)
            }

        }
        return true
    }
    override fun onBackPressed() {
        val fragments = supportFragmentManager.backStackEntryCount
        if (fragments == 1) {
            finish()
            return
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}