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

type BlockContent =
  | {
      type: BlockType.TEXT_BLOCK;
      text: string;
    }
  | {
      type: BlockType.IMAGE_BLOCK;
      images: PageImage[];
    }
  | {
      type: BlockType.MAP_BLOCK;
      places: Place[];
    };

type PageImage = {
  imageId: string;
  imageUrl: string;
  description: string;
  width: number;
  height: number;
};

type Place = {
  id: string | null;
  latitude: number;
  longitude: number;
  title: string;
  description: string;
};

export type { Page, Block, BlockContent, PageImage, Place };
