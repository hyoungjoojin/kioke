import env from '@/util/env';
import {
  Node,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import { useState } from 'react';
import Map, { Marker } from 'react-map-gl/mapbox';

import 'mapbox-gl/dist/mapbox-gl.css';

export const MapNode = Node.create({
  name: 'map',
  group: 'block',
  content: 'inline*',
  addAttributes() {
    return {};
  },
  renderHTML({ HTMLAttributes }) {
    return ['kioke-map', mergeAttributes(HTMLAttributes)];
  },
  parseHTML() {
    return [
      {
        tag: 'kioke-map',
      },
    ];
  },
  addNodeView() {
    return ReactNodeViewRenderer(KiokeMap);
  },
});

function KiokeMap({}: NodeViewProps) {
  const [sidebarOpen, _setSidebarOpen] = useState(false);
  const [locations, setLocations] = useState<
    {
      lat: number;
      lng: number;
    }[]
  >([]);

  return (
    <NodeViewWrapper>
      <div className='flex bg-card px-5 py-5 rounded-xl h-72'>
        <div className='grow flex items-center justify-center'>
          <Map
            mapboxAccessToken={env.NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN}
            mapStyle='mapbox://styles/mapbox/streets-v9'
            attributionControl={false}
            onClick={(event) => {
              setLocations((locations) => [
                ...locations,
                {
                  lat: event.lngLat.lat,
                  lng: event.lngLat.lng,
                },
              ]);
            }}
          >
            {locations.map((location, index) => {
              return (
                <Marker
                  latitude={location.lat}
                  longitude={location.lng}
                  key={index}
                ></Marker>
              );
            })}
          </Map>
        </div>

        {sidebarOpen ? <div className='w-1/5'></div> : null}
      </div>
    </NodeViewWrapper>
  );
}
