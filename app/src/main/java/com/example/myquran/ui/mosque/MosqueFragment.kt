package com.example.myquran.ui.mosque

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myquran.R
import com.example.myquran.databinding.FragmentMosqueBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest


class MosqueFragment : Fragment(), OnMapReadyCallback {
        private lateinit var binding: FragmentMosqueBinding
        private lateinit var fusedLocationClient: FusedLocationProviderClient
        private lateinit var googleMap: GoogleMap

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentMosqueBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

        }

        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showCurrentLocation()
            } else {
                requestLocationPermission()
            }
        }

        private fun requestLocationPermission() {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        private fun showCurrentLocation() {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap.addMarker(MarkerOptions().position(currentLatLng).title("You are here"))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))

                    searchMosquesNearby(location.latitude, location.longitude)
                }
            }
        }

        private fun searchMosquesNearby(latitude: Double, longitude: Double) {
            Places.initialize(requireContext(), "YOUR_API_KEY")
            val token = AutocompleteSessionToken.newInstance()
            val placesClient = Places.createClient(requireContext())

            val location = LatLng(latitude, longitude)
            val radius = 5000
            val type = "mosque"

            val bounds = LatLngBounds(
                LatLng(location.latitude - radius.toDouble(), location.longitude - radius.toDouble()),
                LatLng(location.latitude + radius.toDouble(), location.longitude + radius.toDouble())
            )

            val request = FindAutocompletePredictionsRequest.builder()
                .setLocationRestriction(RectangularBounds.newInstance(bounds))
                .setQuery(type)
                .setSessionToken(token)
                .build()

            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                for (prediction in response.autocompletePredictions) {
                    val placeId = prediction.placeId

                    val fetchPlaceRequest = FetchPlaceRequest.builder(placeId, listOf(Place.Field.LAT_LNG, Place.Field.NAME))
                        .build()

                    placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener { fetchResponse ->
                        val place = fetchResponse.place
                        val mosqueLatLng = place.latLng
                        if (mosqueLatLng != null) {
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(mosqueLatLng)
                                    .title(place.name)
                            )
                        }
                    }.addOnFailureListener { fetchException ->
                        Log.e("FetchPlaceError", "Failed to fetch place: ${fetchException.message}")
                    }
                }
            }.addOnFailureListener { exception ->
                Log.e("AutocompleteError", "Failed to retrieve autocomplete predictions: ${exception.message}")
            }

        }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}