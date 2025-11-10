import type {
  MapBlockAttributes,
  MarkerAttributes,
} from '@/components/feature/editor/extensions';
import KiokeMap from '@/components/feature/map/KiokeMap';
import MapMarker from '@/components/feature/map/MapMarker';
import { Button } from '@/components/ui/button';
import { ScrollArea } from '@/components/ui/scroll-area';
import { BlockOperationType, BlockType } from '@/types/page';
import { type NodeViewProps } from '@tiptap/react';
import { useClickAway } from '@uidotdev/usehooks';
import { AnimatePresence, motion } from 'motion/react';
import { useRef, useState } from 'react';
import { type MapMouseEvent, type MapRef } from 'react-map-gl/mapbox';
import { v4 as uuidv4 } from 'uuid';

import 'mapbox-gl/dist/mapbox-gl.css';

function MapBlockComponent({ node, updateAttributes }: NodeViewProps) {
  const { pageId, blockId, ops, markers } = node.attrs as MapBlockAttributes;

  const mapRef = useRef<MapRef | null>(null);

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
    <div className='flex bg-card px-5 py-5 rounded-xl h-96'>
      <div className='grow flex items-center justify-center relative'>
        <KiokeMap
          ref={mapRef}
          markers={
            <>
              {markers.map((marker, index) => {
                return (
                  <MapMarker
                    key={index}
                    latitude={marker.latitude}
                    longitude={marker.longitude}
                    onClick={() => {
                      mapRef.current?.flyTo({
                        center: [marker.longitude, marker.latitude],
                        zoom: 14,
                      });
                      setSelectedMarker(marker);
                    }}
                  />
                );
              })}

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
            </>
          }
          markerPositions={markers.map((marker) => ({
            latitude: marker.latitude,
            longitude: marker.longitude,
          }))}
          onClick={mapClickHandler}
        />
      </div>
    </div>
  );
}

export default MapBlockComponent;
