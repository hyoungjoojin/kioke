import Icon from '@/components/ui/icon';
import { useState } from 'react';
import {
  Marker as MapboxMarker,
  type MarkerProps as MapboxMarkerProps,
} from 'react-map-gl/mapbox';

type MarkerProps = MapboxMarkerProps & {
  latitude: number;
  longitude: number;
  onClick?: () => void;
};

function MapMarker({ latitude, longitude, onClick, ...props }: MarkerProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <MapboxMarker latitude={latitude} longitude={longitude} {...props}>
      <div
        className='relative'
        onMouseOver={() => {
          setIsHovered(true);
        }}
        onMouseLeave={() => {
          setIsHovered(false);
        }}
        onClick={onClick}
      >
        <Icon
          className='absolute -top-[24px] -left-[12px]'
          name={isHovered ? 'location-plus' : 'location'}
          size={24}
        />
      </div>
    </MapboxMarker>
  );
}

export default MapMarker;
