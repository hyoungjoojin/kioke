import type { BlockAttributes } from '.';
import BlockWrapper from '@/components/feature/editor/blocks/BlockWrapper';
import MapBlockComponent from '@/components/feature/editor/blocks/MapBlockComponent';
import { BlockType } from '@/types/page';
import {
  Node,
  type NodeConfig,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';

type MapBlockAttributes = BlockAttributes & {
  markers: MarkerAttributes[];
};

type MarkerAttributes = {
  id: string;
  latitude: number;
  longitude: number;
  title: string;
  description: string;
};

const MapBlockConfig: NodeConfig<MapBlockAttributes, {}> = {
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
    return ReactNodeViewRenderer(Block);
  },
};

function Block(props: NodeViewProps) {
  return (
    <NodeViewWrapper>
      <BlockWrapper type={BlockType.MAP_BLOCK} initialContent={{}} {...props}>
        <MapBlockComponent {...props} />
      </BlockWrapper>
    </NodeViewWrapper>
  );
}

export default Node.create(MapBlockConfig);
export type { MapBlockAttributes, MarkerAttributes };
