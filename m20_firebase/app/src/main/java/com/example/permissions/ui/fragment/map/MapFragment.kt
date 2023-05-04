package com.example.permissions.ui.fragment.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.permissions.R
import com.example.permissions.databinding.FragmentMapBinding
import com.example.permissions.model.data.State
import com.example.permissions.model.data.apiAll.Feature
import com.example.permissions.ui.helper.Helper
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.launch

class MapFragment : Fragment(), UserLocationObjectListener {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModels()

    private lateinit var mapView: MapView
    private lateinit var mapKit: MapKit
    private lateinit var mapObjectLayer: MapObjectCollection
    private lateinit var userLocationLayer: UserLocationLayer
    private val currentLocation = MutableLiveData<Point?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requestLocationPermission(requireActivity())

        checkPermission()

        Helper.insets(binding.root)

        mapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()
        mapView = binding.mapView
        mapObjectLayer = mapView.map.mapObjects.addCollection()
        mapView.map.move(CameraPosition(Point(41.311151, 69.279737), 10.0f, 0.0f, 0.0f))

        initView()

        addZoomButtons()

    }

    private fun initView() = with(binding) {

//        getLocation()
//        setCurrentLocation()

        currentLocation.observe(viewLifecycleOwner) {

            viewModel.getAll(it?.latitude ?: return@observe, it.longitude)

            mapView.map.move(CameraPosition(Point(it.latitude, it.longitude), 15.0f, 0.0f, 0.0f))
        }

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect {
                    if (it is State.Success) {
                        it.onSuccessApi?.features?.forEach { cor ->
                            setObjectLocation(
                                cor.geometry.coordinates[1], cor.geometry.coordinates[0], cor
                            )
                        }
                    }
                }

            }

        }

    }

    private fun setObjectLocation(lat: Double, lng: Double, feature: Feature) {
        val mark = mapObjectLayer.addPlacemark(Point(lat, lng))

        mark.userData = feature

        mark.addTapListener(placeMarkTapListener)

        val pinIcon = mark.useCompositeIcon()

        pinIcon.setIcon("pin", imageProvider(R.drawable.client_location), setStyle())

        mapObjectLayer.isVisible = true

    }

    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()

    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()

    }

    private fun setCurrentLocation() {
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)

        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true

        userLocationLayer.setObjectListener(this)


    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(), "android.permission.ACCESS_FINE_LOCATION"
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {

            val locationManager =
                requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager

            val location: Location? = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
            )
            if (location != null) {
                val point = Point(location.latitude, location.longitude)
                currentLocation.value = point
            }
//        }
    }

//    private fun requestLocationPermission(requireActivity: Activity) {
//        if (ContextCompat.checkSelfPermission(
//                requireActivity, "android.permission.ACCESS_FINE_LOCATION"
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                requireActivity, arrayOf("android.permission.ACCESS_FINE_LOCATION"), 1
//            )
//        }
//    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationView.arrow.setIcon(imageProvider(R.drawable.location_i_png), setStyle())

        val pinIcon = userLocationView.pin.useCompositeIcon()

        pinIcon.setIcon("pin", imageProvider(R.drawable.location_i_png), setStyle())

        userLocationView.accuracyCircle.fillColor = Color.LTGRAY and -0x66000001
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
    }

    private fun imageProvider(drawable: Int): ImageProvider {
        return ImageProvider.fromResource(requireContext(), drawable)
    }

    private fun setStyle(): IconStyle {
        return IconStyle().setAnchor(PointF(0.5f, 0.5f)).setRotationType(RotationType.ROTATE)
            .setZIndex(0.5f).setScale(0.8f)
    }

    private fun showPointInfo(mapObject: MapObject, data: Feature) {

        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)

        val pointInfoView: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.show_point_info, null)

        val id = pointInfoView.findViewById<TextView>(R.id.id)
        val name = pointInfoView.findViewById<TextView>(R.id.name)
        val type = pointInfoView.findViewById<TextView>(R.id.type)
        val lat = pointInfoView.findViewById<TextView>(R.id.lat)
        val lon = pointInfoView.findViewById<TextView>(R.id.lon)
        val kinds = pointInfoView.findViewById<TextView>(R.id.kinds)

        id.text = "ID: " + data.id
        name.text = "NAME: " + data.properties.name
        type.text = "TYPE: " + data.type
        lat.text = "LAT: " + data.geometry.coordinates[1].toString()
        lon.text = "LON: " + data.geometry.coordinates[0].toString()
        kinds.text = "KINDS: " + data.properties.kinds

        dialogBuilder.setView(pointInfoView)
        val dialog = dialogBuilder.create()

        dialog.show()
    }

    private fun addZoomButtons() = with(binding) {

        myLocation.setOnClickListener {
            if (userLocationLayer.cameraPosition() != null) {
                mapView.map.move(
                    CameraPosition(userLocationLayer.cameraPosition()!!.target, 15.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )
            }

        }

        minus.setOnClickListener {
            mapView.map.move(
                CameraPosition(
                    mapView.map.cameraPosition.target,
                    mapView.map.cameraPosition.zoom - 1, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }

        plus.setOnClickListener {
            mapView.map.move(
                CameraPosition(
                    mapView.map.cameraPosition.target,
                    mapView.map.cameraPosition.zoom + 1, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 1f),
                null
            )
        }
    }

    private val placeMarkTapListener = MapObjectTapListener { mapObject, _ ->
        if (mapObject is PlacemarkMapObject) {

            val userData = mapObject.getUserData()

            if (userData is Feature) {
                showPointInfo(mapObject, userData)
            }
        }
        true
    }

    private fun checkPermission() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            getLocation()
            setCurrentLocation()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }

    companion object {

        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
        }.toTypedArray()
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                getLocation()
                setCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "Permission is not granted ", Toast.LENGTH_SHORT)
                    .show()
            }
        }


}