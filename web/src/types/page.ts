interface Page {
  pageId: string;
  journalId: string;
  title: string;
  date: Date;
  blocks: Block[];
}

enum BlockType {
  TEXT_BLOCK = 'TEXT_BLOCK',
  GALLERY_BLOCK = 'GALLERY_BLOCK',
  IMAGE_BLOCK = 'IMAGE_BLOCK',
  MAP_BLOCK = 'MAP_BLOCK',
  MARKER_BLOCK = 'MARKER_BLOCK',
}

type Block = {
  id: string;
  after: string | null;
} & (
  | {
      type: BlockType.TEXT_BLOCK;
      text: string;
    }
  | {
      type: BlockType.GALLERY_BLOCK;
    }
  | ImageBlock
  | {
      type: BlockType.MAP_BLOCK;
    }
  | MarkerBlock
);

type ImageBlock = {
  type: BlockType.IMAGE_BLOCK;
  parentId: string;
  imageId: string;
  url: string;
  description: string;
  width: number;
  height: number;
};

type MarkerBlock = {
  type: BlockType.MARKER_BLOCK;
  parentId: string;
  latitude: number;
  longitude: number;
  title: string;
  description: string;
};

enum BlockOperationType {
  UPDATE = 'UPDATE',
  DELETE = 'DELETE',
}

type BlockOperation = {
  timestamp: number;
  blockId: string;
  pageId: string;
} & (DeleteBlockOperation | UpdateBlockOperation);

type DeleteBlockOperation = { op: BlockOperationType.DELETE };

type UpdateBlockOperation = {
  op: BlockOperationType.UPDATE;
} & (
  | {
      type: BlockType.TEXT_BLOCK;
      content: {
        text: string;
      };
    }
  | {
      type: BlockType.IMAGE_BLOCK;
      content: {
        parentId: string;
        imageId: string;
      };
    }
  | {
      type: BlockType.GALLERY_BLOCK;
      content: {};
    }
  | {
      type: BlockType.MAP_BLOCK;
      content: {};
    }
  | {
      type: BlockType.MARKER_BLOCK;
      content: {
        parentId: string;
        latitude: number;
        longitude: number;
        title: string;
        description: string;
      };
    }
);

export { BlockType, BlockOperationType };
export type {
  Page,
  Block,
  ImageBlock,
  BlockOperation,
  DeleteBlockOperation,
  UpdateBlockOperation,
};
