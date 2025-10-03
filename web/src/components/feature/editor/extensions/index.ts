import type { ImageBlockAttributes } from './ImageBlock';
import { MapBlockAttributes } from './MapBlock';
import { BlockType } from '@/constant/block';
import type { Block, BlockContent } from '@/types/page';
import type { JSONContent } from '@tiptap/react';
import type { Node } from 'prosemirror-model';

interface BlockAttributes {
  blockId: string;
  isNew: boolean;
}

function getBlockContent(block: Node): BlockContent {
  const type = BlockType[block.type.name as keyof typeof BlockType];
  if (type === BlockType.TEXT_BLOCK) {
    return {
      type,
      text: JSON.stringify(block.content.toJSON()),
    };
  } else if (type === BlockType.IMAGE_BLOCK) {
    const { images } = block.attrs as ImageBlockAttributes;

    return {
      type,
      images,
    };
  } else if (type === BlockType.MAP_BLOCK) {
    return {
      type,
      locations: [],
    };
  } else {
    throw new Error();
  }
}

function deserializeBlock(block: Block): JSONContent | null {
  const blockContent = block.content;

  if (blockContent.type === BlockType.TEXT_BLOCK) {
    return {
      type: blockContent.type,
      content: blockContent.text ? JSON.parse(blockContent.text) : '',
      attrs: {
        blockId: block.blockId,
        isNew: false,
      },
    };
  } else if (blockContent.type === BlockType.IMAGE_BLOCK) {
    return {
      type: blockContent.type,
      content: undefined,
      attrs: {
        blockId: block.blockId,
        isNew: false,
        images: blockContent.images,
      } as ImageBlockAttributes,
    };
  } else if (blockContent.type === BlockType.MAP_BLOCK) {
    return {
      type: blockContent.type,
      attrs: {
        blockId: block.blockId,
        isNew: false,
        locations: [],
      } as MapBlockAttributes,
    };
  }

  return null;
}

export * from './Document';
export * from './TextBlock';
export * from './ImageBlock';
export * from './MapBlock';
export * from './CommandPalette';
export { type BlockAttributes, getBlockContent, deserializeBlock };
