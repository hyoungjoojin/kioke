import { Marker, type MarkerProps } from 'react-map-gl/mapbox';

type CurrentLocationMarkerProps = MarkerProps;

function CurrentLocationMarker({ ...props }: CurrentLocationMarkerProps) {
  return (
    <Marker {...props}>
      <div className='w-6 h-6 bg-blue-500 border-2 border-white rounded-full shadow-lg animate-pulse' />
    </Marker>
  );
}

export default CurrentLocationMarker;
