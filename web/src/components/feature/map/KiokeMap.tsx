import CurrentLocationMarker from './CurrentLocationMarker';
import { Button } from '@/components/ui/button';
import logger from '@/lib/logger';
import env from '@/util/env';
import { forwardRef, useEffect, useState } from 'react';
import { useImperativeHandle, useRef } from 'react';
import Map, { type MapProps, type MapRef } from 'react-map-gl/mapbox';
import { toast } from 'sonner';

type KiokeMapProps = MapProps & {
  markers?: React.ReactNode;
  markerPositions?: { latitude: number; longitude: number }[];
  children?: React.ReactNode;
  enableCurrentLocation?: boolean;
};

const KiokeMap = forwardRef<MapRef, KiokeMapProps>(
  (
    {
      markers,
      markerPositions,
      children,
      enableCurrentLocation = true,
      ...props
    },
    ref,
  ) => {
    const [currentLocation, setCurrentLocation] = useState<{
      longitude: number;
      latitude: number;
    } | null>(null);

    const mapRef = useRef<MapRef>(null);
    useImperativeHandle(ref, () => mapRef.current as MapRef);

    useEffect(() => {
      if (!enableCurrentLocation || !navigator.geolocation) {
        return;
      }

      const id = navigator.geolocation.watchPosition(
        (position) => {
          setCurrentLocation({
            longitude: position.coords.longitude,
            latitude: position.coords.latitude,
          });
        },
        (error) => {
          logger.error('Error getting current location');
          logger.debug(error);
        },
        {
          maximumAge: 10000,
          timeout: 5000,
        },
      );

      return () => {
        navigator.geolocation.clearWatch(id);
      };
    }, [enableCurrentLocation]);

    const currentLocationButtonClickHandler = () => {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          if (mapRef.current) {
            mapRef.current.flyTo({
              center: [position.coords.longitude, position.coords.latitude],
              zoom: 14,
              essential: true,
            });
          }
        },
        (error) => {
          switch (error.code) {
            case error.PERMISSION_DENIED:
              logger.debug('Geolocation permission denied by user.');
              toast.error('Geolocation permission denied by user.');
              break;

            case error.POSITION_UNAVAILABLE:
              logger.error('Location information is unavailable.');
              toast.error('Location information is unavailable.');
              break;
            case error.TIMEOUT:
              logger.error('The request to get user location timed out.');
              toast.error('The request to get user location timed out.');
              break;
            default:
              logger.error('An unknown error occurred.');
              toast.error('An unknown error occurred.');
              break;
          }
        },
      );
    };

    const changeViewToBoundingBoxButtonClickHandler = () => {
      if (!markerPositions || markerPositions.length === 0) {
        return;
      }

      const latitudes = markerPositions
          .map((marker) => marker.latitude)
          .sort((a, b) => a - b),
        longitudes = markerPositions
          .map((marker) => marker.longitude)
          .sort((a, b) => a - b);

      const west = longitudes[0],
        east = longitudes[longitudes.length - 1],
        south = latitudes[0],
        north = latitudes[latitudes.length - 1];

      if (mapRef.current) {
        mapRef.current.fitBounds([west, south, east, north], {
          padding: 50,
          animate: true,
        });
      }
    };

    return (
      <div className='relative w-full h-full'>
        <Map
          ref={mapRef}
          mapboxAccessToken={env.NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN}
          mapStyle='mapbox://styles/mapbox/standard'
          attributionControl={false}
          projection='globe'
          {...props}
        >
          {markers}
          {enableCurrentLocation && currentLocation && (
            <CurrentLocationMarker
              latitude={currentLocation.latitude}
              longitude={currentLocation.longitude}
            />
          )}
        </Map>

        <div className='absolute top-4 left-4 flex gap-2'>
          <Button
            onClick={currentLocationButtonClickHandler}
            icon='location-plus'
            variant='icon'
            className='bg-card'
          />

          <Button
            onClick={changeViewToBoundingBoxButtonClickHandler}
            icon='plus'
            variant='icon'
            className='bg-card'
          />
        </div>

        {children}
      </div>
    );
  },
);
KiokeMap.displayName = 'KiokeMap';

export default KiokeMap;
