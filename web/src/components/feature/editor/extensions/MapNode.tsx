import env from '@/util/env';
import {
  Node,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import { Map as Mapbox } from 'mapbox-gl';
import mapboxgl from 'mapbox-gl';
import { useEffect, useRef } from 'react';

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
    return ReactNodeViewRenderer(Map);
  },
});

function Map({}: NodeViewProps) {
  const mapRef = useRef<Mapbox | null>(null);
  const mapContainerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (mapContainerRef.current) {
      mapboxgl.accessToken = env.NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN;
      mapRef.current = new Mapbox({
        container: mapContainerRef.current,
      });
    }

    return () => {
      if (mapRef.current) {
        mapRef.current.remove();
      }
    };
  }, []);

  return (
    <NodeViewWrapper>
      <div className='flex items-center'>
        <div ref={mapContainerRef} className='hover:cursor-pointer'></div>
      </div>
    </NodeViewWrapper>
  );
}
