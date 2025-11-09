import GalleryBlock, { type GalleryBlockAttributes } from './GalleryBlock';
import MapBlock, {
  type MapBlockAttributes,
  type MarkerAttributes,
} from './MapBlock';
import TextBlock, { type TextBlockAttributes } from './TextBlock';
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

function deserializeBlocks(pageId: string, blocks: Block[]): JSONContent[] {
  const result: JSONContent[] = [];

  for (const block of blocks) {
    if (block.type === BlockType.TEXT_BLOCK) {
      const { type, id, text } = block;

      result.push({
        type,
        content: text ? JSON.parse(text) : '',
        attrs: {
          pageId,
          blockId: id,
          ops: [],
        } satisfies TextBlockAttributes,
      });
    } else if (block.type === BlockType.GALLERY_BLOCK) {
      const { type, id } = block;

      result.push({
        type,
        attrs: {
          pageId,
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
    } else if (block.type === BlockType.MAP_BLOCK) {
      const { type, id } = block;

      result.push({
        type,
        attrs: {
          pageId,
          blockId: id,
          ops: [],
          markers: blocks
            .filter((block) => block.type === BlockType.MARKER_BLOCK)
            .filter((block) => block.parentId === id)
            .map((block) => ({
              id: block.id,
              latitude: block.latitude,
              longitude: block.longitude,
              title: block.title,
              description: block.description,
            })),
        } satisfies MapBlockAttributes,
      });
    }
  }

  return result;
}

export * from './Document';
export * from './CommandPalette';

export { deserializeBlocks, TextBlock, GalleryBlock, MapBlock };
export type {
  BlockOptions,
  BlockAttributes,
  TextBlockAttributes,
  MapBlockAttributes,
  GalleryBlockAttributes,
  MarkerAttributes,
};
