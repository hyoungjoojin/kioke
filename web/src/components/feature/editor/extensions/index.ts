import { BlockType } from '@/constant/block';
import type { BlockContent } from '@/types/page';
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
  } else {
    throw new Error();
  }
}

export * from './Document';
export * from './TextBlock';
export * from './ImageBlock';
export * from './MapBlock';
export * from './CommandPalette';
export { type BlockAttributes, getBlockContent };
