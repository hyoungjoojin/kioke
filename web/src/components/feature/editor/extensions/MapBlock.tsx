import type { BlockAttributes } from '.';
import Icon from '@/components/ui/icon';
import { BlockType } from '@/constant/block';
import type { Place } from '@/types/page';
import env from '@/util/env';
import {
  Node,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import { AnimatePresence, motion } from 'motion/react';
import { useState } from 'react';
import Map, { type MapMouseEvent, Marker } from 'react-map-gl/mapbox';
import { v4 as uuidv4 } from 'uuid';

import 'mapbox-gl/dist/mapbox-gl.css';

import { Button } from '@/components/ui/button';
import { ScrollArea } from '@/components/ui/scroll-area';
import { useClickAway } from '@uidotdev/usehooks';

export type MapBlockAttributes = BlockAttributes & {
  places: Place[];
};

const MapBlock = Node.create({
  name: BlockType.MAP_BLOCK,
  group: 'block',
  content: '',
  atom: true,
  selectable: false,
  addAttributes() {
    return {
      blockId: {
        default: () => uuidv4(),
      },
      isNew: {
        default: true,
      },
      places: {
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
  const { places } = node.attrs as MapBlockAttributes;

  const [selectedPlace, setSelectedPlace] = useState<Place | null>(null);

  const sidebarRef = useClickAway<HTMLDivElement>(() => {
    setSelectedPlace(null);
  });

  const mapClickHandler = (event: MapMouseEvent) => {
    if (selectedPlace) {
      return;
    }

    updateAttributes({
      places: [
        ...places,
        {
          id: null,
          latitude: event.lngLat.lat,
          longitude: event.lngLat.lng,
          title: 'Lorem Ipsum',
          description:
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
        },
      ],
    });
  };

  const PlaceMarker = ({ place }: { place: Place }) => {
    const [isHovered, setIsHovered] = useState(false);

    return (
      <Marker latitude={place.latitude} longitude={place.longitude}>
        <div
          className='relative'
          onMouseOver={() => {
            setIsHovered(true);
          }}
          onMouseLeave={() => {
            setIsHovered(false);
          }}
          onClick={() => {
            setSelectedPlace(place);
          }}
        >
          <Icon
            className='absolute -top-[24px] -left-[12px]'
            name={isHovered ? 'location-plus' : 'location'}
            size={24}
          />
        </div>
      </Marker>
    );
  };

  const Sidebar = () => {
    if (!selectedPlace) {
      return null;
    }

    const editButtonClickHandler = () => {
      // TODO: Make the title and description editable
    };

    const trashButtonClickHandler = () => {
      // TODO: Need to remove place from list
      setSelectedPlace(null);
    };

    const closeButtonClickHandler = () => {
      setSelectedPlace(null);
    };

    return (
      <div ref={sidebarRef} className='absolute right-0 top-0 h-full p-2 w-56'>
        <div className='bg-card h-full rounded-3xl p-3 flex flex-col'>
          <div className='self-end'>
            <Button
              variant='icon'
              icon='edit'
              onClick={editButtonClickHandler}
            />

            <Button
              variant='icon'
              icon='trash'
              onClick={trashButtonClickHandler}
            />

            <Button variant='icon' icon='x' onClick={closeButtonClickHandler} />
          </div>

          <ScrollArea className='h-full overflow-y-hidden'>
            <div className='font-bold text-md mb-2'>{selectedPlace.title}</div>
            <div className='text-xs'>{selectedPlace.description}</div>
          </ScrollArea>
        </div>
      </div>
    );
  };

  return (
    <NodeViewWrapper>
      <div className='flex bg-card px-5 py-5 rounded-xl h-96'>
        <div className='grow flex items-center justify-center relative'>
          <Map
            mapboxAccessToken={env.NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN}
            mapStyle='mapbox://styles/mapbox/streets-v9'
            attributionControl={false}
            onClick={mapClickHandler}
          >
            {places.map((place, index) => {
              return <PlaceMarker key={index} place={place} />;
            })}
          </Map>

          {selectedPlace && (
            <AnimatePresence>
              <motion.div
                initial={{
                  opacity: 0,
                }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{
                  duration: 0.1,
                }}
              >
                <Sidebar />
              </motion.div>
            </AnimatePresence>
          )}
        </div>
      </div>
    </NodeViewWrapper>
  );
}

export { MapBlock };
