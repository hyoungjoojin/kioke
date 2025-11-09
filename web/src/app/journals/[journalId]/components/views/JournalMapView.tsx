'use client';

import KiokeMap from '@/components/feature/map/KiokeMap';
import { useJournalQuery } from '@/query/journal';
import { pageMarkersQueryOptions } from '@/query/page';
import { useQueries } from '@tanstack/react-query';
import { useRef } from 'react';
import { type MapRef, Marker } from 'react-map-gl/mapbox';

import 'mapbox-gl/dist/mapbox-gl.css';

interface JournalMapViewProps {
  journalId: string;
}

function JournalMapView({ journalId }: JournalMapViewProps) {
  const mapRef = useRef<MapRef | null>(null);

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
    <KiokeMap
      ref={mapRef}
      markers={
        <>
          {markers
            .filter((marker) => marker !== undefined)
            .map((marker, index) => {
              return (
                <Marker
                  key={index}
                  latitude={marker.latitude}
                  longitude={marker.longitude}
                />
              );
            })}
        </>
      }
      markerPositions={markers
        .filter((marker) => marker !== undefined)
        .map((marker) => ({
          latitude: marker.latitude,
          longitude: marker.longitude,
        }))}
    />
  );
}

export default JournalMapView;
