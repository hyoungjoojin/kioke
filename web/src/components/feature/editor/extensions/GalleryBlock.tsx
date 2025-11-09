import type { BlockAttributes } from '.';
import BlockWrapper from '@/components/feature/editor/blocks/BlockWrapper';
import GalleryBlockComponent from '@/components/feature/editor/blocks/GalleryBlockComponent';
import { BlockType } from '@/types/page';
import {
  Node,
  type NodeConfig,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';

type GalleryBlockAttributes = BlockAttributes & {
  images: ImageAttributes[];
};

type ImageAttributes = {
  status: 'success' | 'pending' | 'failed';
  error?: string;
  id: string;
  url: string;
  width: number;
  height: number;
  description: string;
};

const GalleryBlockConfig: NodeConfig<GalleryBlockAttributes, {}> = {
  name: BlockType.GALLERY_BLOCK,
  group: 'block',
  content: '',
  atom: true,
  selectable: true,
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
      images: {
        default: [],
      },
    };
  },
  renderHTML({ HTMLAttributes }) {
    return [BlockType.GALLERY_BLOCK, mergeAttributes(HTMLAttributes)];
  },
  parseHTML() {
    return [
      {
        tag: BlockType.GALLERY_BLOCK,
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
      <BlockWrapper
        type={BlockType.GALLERY_BLOCK}
        initialContent={{}}
        {...props}
      >
        <GalleryBlockComponent {...props} />
      </BlockWrapper>
    </NodeViewWrapper>
  );
}

export default Node.create(GalleryBlockConfig);
export type { GalleryBlockAttributes };
