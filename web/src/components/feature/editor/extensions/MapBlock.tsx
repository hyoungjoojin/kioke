import type { BlockAttributes } from '.';
import { Button } from '@/components/ui/button';
import Icon from '@/components/ui/icon';
import { ScrollArea } from '@/components/ui/scroll-area';
import { BlockOperationType, BlockType } from '@/types/page';
import env from '@/util/env';
import {
  Node,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import { useClickAway } from '@uidotdev/usehooks';
import { AnimatePresence, motion } from 'motion/react';
import { useEffect, useState } from 'react';
import Map, {
  type MapMouseEvent,
  Marker as MapboxMarker,
} from 'react-map-gl/mapbox';
import { v4 as uuidv4 } from 'uuid';

import 'mapbox-gl/dist/mapbox-gl.css';

type MarkerAttributes = {
  id: string;
  latitude: number;
  longitude: number;
  title: string;
  description: string;
};

type MapBlockAttributes = BlockAttributes & {
  markers: MarkerAttributes[];
};

const MapBlock = Node.create({
  name: BlockType.MAP_BLOCK,
  group: 'block',
  content: '',
  atom: true,
  addAttributes() {
    return {
      blockId: {
        default: null,
      },
      pageId: {
        default: () => {
          if (!this.options.pageId) {
            throw new Error('Page ID must be configured for blocks');
          }
          return this.options.pageId;
        },
      },
      ops: {
        default: [],
      },
      markers: {
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
  const { pageId, blockId, ops, markers } = node.attrs as MapBlockAttributes;

  useEffect(() => {
    if (blockId === null) {
      setTimeout(() => {
        const blockId = uuidv4();

        updateAttributes({
          pageId,
          blockId,
          ops: [
            ...ops,
            {
              timestamp: Date.now(),
              op: BlockOperationType.UPDATE,
              pageId,
              blockId,
              type: BlockType.MAP_BLOCK,
              content: {},
            },
          ],
          markers,
        } satisfies MapBlockAttributes);
      }, 0);
    }
  }, [blockId, pageId, ops, updateAttributes, markers]);

  const [selectedMarker, setSelectedMarker] = useState<MarkerAttributes | null>(
    null,
  );

  const sidebarRef = useClickAway<HTMLDivElement>(() => {
    setSelectedMarker(null);
  });

  const mapClickHandler = (event: MapMouseEvent) => {
    if (selectedMarker) {
      return;
    }

    const content = {
      latitude: event.lngLat.lat,
      longitude: event.lngLat.lng,
      title: 'Untitled',
      description: '',
    };

    updateAttributes({
      ops: [
        ...ops,
        {
          timestamp: Date.now(),
          op: BlockOperationType.UPDATE,
          pageId,
          blockId: uuidv4(),
          type: BlockType.MARKER_BLOCK,
          content: {
            parentId: blockId,
            ...content,
          },
        },
      ],
      markers: [
        ...markers,
        {
          id: uuidv4(),
          ...content,
        },
      ],
    } satisfies Partial<MapBlockAttributes>);
  };

  const Marker = ({ marker }: { marker: MarkerAttributes }) => {
    const [isHovered, setIsHovered] = useState(false);

    return (
      <MapboxMarker latitude={marker.latitude} longitude={marker.longitude}>
        <div
          className='relative'
          onMouseOver={() => {
            setIsHovered(true);
          }}
          onMouseLeave={() => {
            setIsHovered(false);
          }}
          onClick={() => {
            setSelectedMarker(marker);
          }}
        >
          <Icon
            className='absolute -top-[24px] -left-[12px]'
            name={isHovered ? 'location-plus' : 'location'}
            size={24}
          />
        </div>
      </MapboxMarker>
    );
  };

  const Sidebar = () => {
    if (!selectedMarker) {
      return null;
    }

    const editButtonClickHandler = () => {
      // TODO: Make the title and description editable
    };

    const trashButtonClickHandler = () => {
      // TODO: Need to remove place from list
      setSelectedMarker(null);
    };

    const closeButtonClickHandler = () => {
      setSelectedMarker(null);
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
            <div className='font-bold text-md mb-2'>{selectedMarker.title}</div>
            <div className='text-xs'>{selectedMarker.description}</div>
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
            {markers.map((marker, index) => {
              return <Marker key={index} marker={marker} />;
            })}
          </Map>

          {selectedMarker && (
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

export type { MapBlockAttributes };
export { MapBlock };
