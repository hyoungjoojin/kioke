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
} & BlockContent;

type BlockContent = {
  type: BlockType.TEXT_BLOCK;
  text: string;
};

export type { Page, Block, BlockContent };
