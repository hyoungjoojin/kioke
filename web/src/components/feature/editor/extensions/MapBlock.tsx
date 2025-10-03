import type { BlockAttributes } from '.';
import { BlockType } from '@/constant/block';
import env from '@/util/env';
import {
  Node,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import Map, { type MapMouseEvent, Marker } from 'react-map-gl/mapbox';
import { v4 as uuidv4 } from 'uuid';

import 'mapbox-gl/dist/mapbox-gl.css';

export type MapBlockAttributes = BlockAttributes & {
  locations: {
    latitude: number;
    longitude: number;
  }[];
};

const MapBlock = Node.create({
  name: BlockType.MAP_BLOCK,
  group: 'block',
  content: '',
  atom: true,
  selectable: true,
  addAttributes() {
    return {
      blockId: {
        default: () => uuidv4(),
      },
      isNew: {
        default: true,
      },
      locations: {
        default: [],
      },
    };
  },
  renderHTML({ HTMLAttributes }) {
    return [BlockType.MAP_BLOCK, mergeAttributes(HTMLAttributes)];
  },
  parseHTML() {
    return [
      {
        tag: BlockType.MAP_BLOCK,
      },
    ];
  },
  addNodeView() {
    return ReactNodeViewRenderer(KiokeMap);
  },
});

function KiokeMap({ node, updateAttributes }: NodeViewProps) {
  const { locations } = node.attrs as MapBlockAttributes;

  const mapClickHandler = (event: MapMouseEvent) => {
    updateAttributes({
      locations: [
        ...locations,
        {
          latitude: event.lngLat.lat,
          longitude: event.lngLat.lng,
        },
      ],
    });
  };

  return (
    <NodeViewWrapper>
      <div className='flex bg-card px-5 py-5 rounded-xl h-72'>
        <div className='grow flex items-center justify-center'>
          <Map
            mapboxAccessToken={env.NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN}
            mapStyle='mapbox://styles/mapbox/streets-v9'
            attributionControl={false}
            onClick={mapClickHandler}
          >
            {locations.map((location, index) => {
              return (
                <Marker
                  latitude={location.latitude}
                  longitude={location.longitude}
                  key={index}
                ></Marker>
              );
            })}
          </Map>
        </div>
      </div>
    </NodeViewWrapper>
  );
}

export { MapBlock };
