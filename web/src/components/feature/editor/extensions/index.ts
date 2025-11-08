import type { GalleryBlockAttributes } from './GalleryBlock';
import type { TextBlockAttributes } from './TextBlock';
import { type Block, type BlockOperation, BlockType } from '@/types/page';
import type { JSONContent } from '@tiptap/react';

interface BlockOptions {
  pageId: string;
}

interface BlockAttributes {
  pageId: string;
  blockId: string;
  ops: BlockOperation[];
}

function deserializeBlocks(blocks: Block[]): JSONContent[] {
  const result: JSONContent[] = [];

  for (const block of blocks) {
    if (block.type === BlockType.TEXT_BLOCK) {
      const { type, id, text } = block;

      result.push({
        type,
        content: text ? JSON.parse(text) : '',
        attrs: {
          pageId: '',
          blockId: id,
          ops: [],
        } satisfies TextBlockAttributes,
      });
    } else if (block.type === BlockType.GALLERY_BLOCK) {
      const { type, id } = block;

      result.push({
        type,
        attrs: {
          pageId: '',
          blockId: id,
          ops: [],
          images: blocks
            .filter((block) => block.type === BlockType.IMAGE_BLOCK)
            .filter((block) => block.parentId === id)
            .map((block) => ({
              status: 'success',
              id: block.id,
              url: block.url,
              description: block.description || '',
              width: block.width,
              height: block.height,
            })),
        } satisfies GalleryBlockAttributes,
      });
    }
  }

  return result;
}

export * from './Document';
export * from './TextBlock';
export * from './GalleryBlock';
export * from './MapBlock';
export * from './CommandPalette';
export { type BlockOptions, type BlockAttributes, deserializeBlocks };
