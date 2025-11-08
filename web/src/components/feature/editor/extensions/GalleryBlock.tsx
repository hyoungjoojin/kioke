import type { BlockAttributes, BlockOptions } from '.';
import { uploadImage } from '@/app/api/image';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Spinner } from '@/components/ui/spinner';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import logger from '@/lib/logger';
import { cn } from '@/lib/utils';
import { BlockOperationType, BlockType } from '@/types/page';
import { getImageDimensions } from '@/util/image';
import { unwrap } from '@/util/result';
import {
  Node,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import Image from 'next/image';
import { type ChangeEvent, useEffect } from 'react';
import { useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

type GalleryBlockAttributes = BlockAttributes & {
  images: {
    status: 'success' | 'pending' | 'failed';
    error?: string;
    id: string;
    url: string;
    width: number;
    height: number;
    description: string;
  }[];
};

const GalleryBlock = Node.create<BlockOptions>({
  name: BlockType.GALLERY_BLOCK,
  group: 'block',
  content: '',
  atom: true,
  selectable: true,
  addOptions() {
    return {
      pageId: '',
    };
  },
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
    return ReactNodeViewRenderer(GalleryBlockComponent);
  },
});

function GalleryBlockComponent({
  node,
  updateAttributes,
  selected,
}: NodeViewProps) {
  const { pageId, blockId, ops, images } = node.attrs as GalleryBlockAttributes;

  const [modal, setModal] = useState<{ open: boolean; index: number }>({
    open: false,
    index: 0,
  });

  useEffect(() => {
    if (blockId === null) {
      setTimeout(() => {
        const blockId = uuidv4();

        updateAttributes({
          pageId,
          blockId,
          ops: [
            ...ops,
            {
              timestamp: Date.now(),
              op: BlockOperationType.UPDATE,
              pageId,
              blockId,
              type: BlockType.GALLERY_BLOCK,
              content: {},
            },
          ],
          images: [],
        } satisfies GalleryBlockAttributes);
      }, 0);
    }
  }, [blockId, pageId, ops, updateAttributes]);

  const fileUploadHandler = async (e: ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (!files || files.length === 0) {
      return;
    }

    logger.debug(`Uploading ${files.length} images to gallery block`);

    const uploadedImages = (
      await Promise.all(
        Array.from(files)
          .map((file) => ({
            imageId: uuidv4(),
            file,
          }))
          .map(async ({ imageId, file }) => ({
            id: imageId,
            file,
            status: 'pending' as const,
            ...(await getImageDimensions(URL.createObjectURL(file)).catch(
              (e) => {
                logger.error(
                  'Failed to get image dimensions for uploaded image: ',
                  e,
                );

                return {
                  width: 500,
                  height: 500,
                  status: 'failed' as const,
                  error: 'Failed to load image',
                };
              },
            )),
          })),
      )
    ).filter((image) => image.status !== 'failed');

    updateAttributes({
      pageId,
      blockId,
      ops,
      images: [
        ...images,
        ...uploadedImages.map((image) => ({
          ...image,
          id: '',
          url: URL.createObjectURL(image.file),
          width: image.width,
          height: image.height,
          description: '',
        })),
      ],
    } satisfies GalleryBlockAttributes);

    logger.debug(`Uploading ${uploadedImages.length} images to server`);

    const finalImages = await Promise.all(
      uploadedImages.map(async ({ id, file, width, height }) => ({
        id,
        result: await uploadImage({
          file: file,
          width,
          height,
        }),
      })),
    );

    updateAttributes({
      pageId,
      blockId,
      ops: [
        ...ops,
        ...finalImages
          .filter(({ result }) => result.isOk())
          .map(({ result }) => {
            const imageData = unwrap(result);

            return {
              timestamp: Date.now(),
              op: BlockOperationType.UPDATE as const,
              pageId,
              blockId: uuidv4(),
              type: BlockType.IMAGE_BLOCK as const,
              content: {
                parentId: blockId,
                imageId: imageData.id,
              },
            };
          }),
      ],
      images: images.map((original) => {
        const finalImage = finalImages.find(
          (image) => image.id === original.id,
        );
        if (!finalImage) {
          return original;
        }

        if (finalImage.result.isErr()) {
          return {
            ...original,
            status: 'failed' as const,
            error: finalImage.result.error.message,
          };
        }

        return {
          ...original,
          status: 'success' as const,
          id: finalImage.result.value.id,
        };
      }),
    } satisfies GalleryBlockAttributes);
  };

  if (!images || images.length === 0) {
    return (
      <NodeViewWrapper>
        <Card className={cn('my-1', selected && 'bg-red-300/40 shadow-md')}>
          <CardContent>
            <Tabs defaultValue='upload'>
              <TabsList>
                <TabsTrigger value='upload'>Upload Image</TabsTrigger>
              </TabsList>

              <TabsContent value='upload'>
                <Input
                  type='file'
                  accept='image/*'
                  onChange={fileUploadHandler}
                  multiple
                />
              </TabsContent>
            </Tabs>
          </CardContent>
        </Card>
      </NodeViewWrapper>
    );
  }

  return (
    <NodeViewWrapper>
      {modal.open && (
        <div
          className='fixed inset-0 z-50 flex items-center justify-center bg-[rgb(0,0,0,0.5)]'
          onClick={() => setModal((modal) => ({ ...modal, open: false }))}
        >
          <div
            className='relative flex flex-col items-center justify-center'
            style={{ maxWidth: '90vw', maxHeight: '90vh' }}
            onClick={(e) => e.stopPropagation()}
          >
            <Image
              src={images[modal.index].url}
              alt={images[modal.index].description}
              width={images[modal.index].width}
              height={images[modal.index].height}
              className='object-contain rounded-lg shadow-lg'
              style={{ maxWidth: '80vw', maxHeight: '80vh' }}
            />

            <Button
              className='absolute left-2 top-1/2 -translate-y-1/2 bg-white bg-opacity-70 rounded-full p-2 shadow hover:bg-opacity-90'
              onClick={() =>
                setModal((modal) => ({
                  ...modal,
                  index: modal.index > 0 ? modal.index - 1 : modal.index,
                }))
              }
              disabled={modal.index === 0}
              style={{ zIndex: 10 }}
              aria-label='Previous image'
            >
              &#8592;
            </Button>
            <Button
              className='absolute right-2 top-1/2 -translate-y-1/2 bg-white bg-opacity-70 rounded-full p-2 shadow hover:bg-opacity-90'
              onClick={() =>
                setModal((modal) => ({
                  ...modal,
                  index:
                    modal.index < images.length - 1
                      ? modal.index + 1
                      : modal.index,
                }))
              }
              disabled={modal.index === images.length - 1}
              style={{ zIndex: 10 }}
              aria-label='Next image'
            >
              &#8594;
            </Button>
            <Button
              className='absolute top-4 right-4 bg-white bg-opacity-80 rounded-full p-2 shadow hover:bg-opacity-100'
              onClick={() => setModal((modal) => ({ ...modal, open: false }))}
              style={{ zIndex: 20 }}
              aria-label='Close image modal'
            >
              &#10005;
            </Button>
          </div>
        </div>
      )}

      <div className='flex justify-center overflow-x-auto gap-4 p-2'>
        {images.map((image, index) => {
          const height = 250;
          const width = (height * image.width) / image.height;

          return (
            <div
              key={index}
              style={{ height: `${height}px`, width: `${width}px` }}
              className='relative cursor-pointer'
              onClick={() => {
                setModal({ open: true, index });
              }}
            >
              <Image
                src={image.url}
                alt={image.description}
                fill
                className={
                  image.status === 'failed'
                    ? 'object-cover blur-sm brightness-75'
                    : 'object-cover'
                }
                style={{ zIndex: 0 }}
              />

              {image.status === 'pending' && (
                <div className='absolute inset-0 flex items-center justify-center'></div>
              )}

              {image.status !== 'success' && (
                <div
                  className='absolute inset-0 flex items-center justify-center'
                  style={{
                    zIndex: 1,
                    pointerEvents: 'none',
                  }}
                >
                  {image.status === 'pending' && <Spinner />}
                  {image.status === 'failed' && (
                    <span className='bg-black bg-opacity-60 text-white px-4 py-2 rounded text-center text-base font-semibold shadow-lg'>
                      {image.error}
                    </span>
                  )}
                </div>
              )}
            </div>
          );
        })}
      </div>
    </NodeViewWrapper>
  );
}

export type { GalleryBlockAttributes };
export { GalleryBlock };
