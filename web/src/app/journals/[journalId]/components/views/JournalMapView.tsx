'use client';

import env from '@/util/env';
import Map, { Marker } from 'react-map-gl/mapbox';

import 'mapbox-gl/dist/mapbox-gl.css';

import { useJournalQuery } from '@/query/journal';
import { pageMarkersQueryOptions } from '@/query/page';
import { useQueries } from '@tanstack/react-query';

interface JournalMapViewProps {
  journalId: string;
}

function JournalMapView({ journalId }: JournalMapViewProps) {
  const { data: journal } = useJournalQuery({ journalId });
  const pageIds = journal ? journal.pages.map((page) => page.id) : [];
  const { markers } = useQueries({
    queries: pageIds.map((pageId) => ({
      ...pageMarkersQueryOptions({ id: pageId }),
      enabled: !!journal,
    })),
    combine: (results) => ({
      markers: results.map((result) => result.data).flat(),
      pending: results.some((result) => result.isPending),
    }),
  });

  if (!journal) {
    return null;
  }

  return (
    <div className='h-full'>
      <Map
        mapboxAccessToken={env.NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN}
        mapStyle='mapbox://styles/mapbox/standard'
        attributionControl={false}
        projection='globe'
      >
        {markers
          .filter((marker) => marker !== undefined)
          .map((marker, index) => {
            return (
              <Marker
                key={index}
                longitude={marker.longitude}
                latitude={marker.longitude}
              />
            );
          })}
      </Map>
    </div>
  );
}

export default JournalMapView;
