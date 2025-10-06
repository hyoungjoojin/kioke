import type { BlockType } from '@/constant/block';

interface Page {
  pageId: string;
  journalId: string;
  title: string;
  date: Date;
  blocks: Block[];
}

type Block = {
  blockId: string;
  content: BlockContent;
};

type Place = {
  id: string | null;
  latitude: number;
  longitude: number;
  title: string;
  description: string;
};

type BlockContent =
  | {
      type: BlockType.TEXT_BLOCK;
      text: string;
    }
  | {
      type: BlockType.IMAGE_BLOCK;
      images: {
        imageId: string;
        description: string;
      }[];
    }
  | {
      type: BlockType.MAP_BLOCK;
      places: Place[];
    };

export type { Page, Block, BlockContent, Place };
