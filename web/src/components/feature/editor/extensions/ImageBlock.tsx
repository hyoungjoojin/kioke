import type { BlockAttributes } from '.';
import { uploadImage } from '@/app/api/image';
import { getImage } from '@/app/api/image';
import { Card, CardContent } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { BlockType } from '@/constant/block';
import { ErrorCode } from '@/constant/error';
import logger from '@/lib/logger';
import KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import {
  Node,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import { default as NextImage } from 'next/image';
import { type ChangeEvent, useEffect, useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

type ImageBlockAttributes = BlockAttributes & {
  images: {
    imageId: string;
    description: string;
  }[];
};

const ImageBlock = Node.create({
  name: BlockType.IMAGE_BLOCK,
  group: 'block',
  content: 'inline*',
  addAttributes() {
    return {
      blockId: {
        default: () => uuidv4(),
      },
      isNew: {
        default: true,
      },
      images: {
        default: [],
      },
    };
  },
  renderHTML({ HTMLAttributes }) {
    return [BlockType.IMAGE_BLOCK, mergeAttributes(HTMLAttributes)];
  },
  parseHTML() {
    return [
      {
        tag: BlockType.IMAGE_BLOCK,
      },
    ];
  },
  addNodeView() {
    return ReactNodeViewRenderer(Image);
  },
});

type ImageState = {
  imageId: string;
  description: string;
} & (
  | { loadStatus: 'pending' }
  | { loadStatus: 'loaded'; src: string; width: number; height: number }
  | { loadStatus: 'failed'; error: KiokeError }
) &
  (
    | { uploadStatus: 'pending' | 'uploaded' }
    | { uploadStatus: 'failed'; error: KiokeError }
  );

function Image({ node, updateAttributes }: NodeViewProps) {
  const { blockId, images: imageData } = node.attrs as ImageBlockAttributes;

  const [images, setImages] = useState<ImageState[]>(
    imageData.map((image) => ({
      ...image,
      loadStatus: 'pending',
      uploadStatus: 'uploaded',
    })),
  );

  useEffect(() => {
    if (
      images.length === 0 ||
      !images.some((image) => image.loadStatus === 'pending')
    ) {
      return;
    }

    const pendingImageIds = images
      .filter((image) => image.loadStatus === 'pending')
      .map((image) => image.imageId);

    const getImages = async () => {
      const imageMap = new Map<
        string,
        | {
            success: true;
            imageId: string;
            url: string;
            width: number;
            height: number;
          }
        | { success: false; error: KiokeError }
      >();

      await getImage({
        context: 'IMAGE_BLOCK',
        contextId: blockId,
        ids: pendingImageIds,
      })
        .then((result) => unwrap(result))
        .then((data) =>
          data.forEach((data) =>
            imageMap.set(data.imageId, { success: true, ...data }),
          ),
        )
        .catch((error) => {
          logger.debug(error);
          pendingImageIds.forEach((imageId) =>
            imageMap.set(imageId, { success: false, error }),
          );
        });

      setImages((images) =>
        images.map((image) => {
          const data = imageMap.get(image.imageId);
          if (!data) {
            return image;
          }

          if (data.success) {
            return {
              imageId: image.imageId,
              description: '',
              loadStatus: 'loaded',
              src: data.url,
              width: data.width,
              height: data.height,
              uploadStatus: 'uploaded',
            };
          } else {
            return {
              imageId: image.imageId,
              description: '',
              loadStatus: 'failed',
              error: data.error,
              uploadStatus: 'uploaded',
            };
          }
        }),
      );
    };

    getImages();
  }, [blockId, images]);

  const fileInputChangeHandler = async (e: ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (!files || files.length === 0) {
      return;
    }

    const newImages = await Promise.all(
      Array.from(files)
        .map((file) => ({
          imageId: uuidv4(),
          file,
        }))
        .map(
          ({ imageId, file }) =>
            new Promise<{
              imageId: string;
              file: File;
              width: number;
              height: number;
            }>((resolve) => {
              const image = new window.Image();
              image.onload = () => {
                const src = URL.createObjectURL(file),
                  width = image.naturalWidth,
                  height = image.naturalHeight;

                setImages((images) => [
                  {
                    imageId,
                    description: '',
                    loadStatus: 'loaded',
                    src,
                    width,
                    height,
                    uploadStatus: 'pending',
                  },
                  ...images,
                ]);

                resolve({ imageId, file, width, height });
              };

              image.src = URL.createObjectURL(file);
            }),
        ),
    );

    const newImageIds = await Promise.all(
      newImages.map(async ({ imageId, file, width, height }) => {
        const uploadImageResponse = await uploadImage({
          name: file.name,
          contentType: file.type,
          contentLength: file.size,
          width,
          height,
        });

        if (uploadImageResponse.isErr()) {
          setImages((images) =>
            images.map((image) =>
              image.imageId === imageId
                ? {
                    ...image,
                    uploadStatus: 'failed',
                    error: uploadImageResponse.error,
                  }
                : image,
            ),
          );
          return;
        }

        const response = await fetch(uploadImageResponse.value.signedPostUrl, {
          method: 'PUT',
          body: file,
          headers: {
            'Content-Type': file.type,
          },
        });

        if (!response.ok) {
          setImages((images) =>
            images.map((image) =>
              image.imageId === imageId
                ? {
                    ...image,
                    uploadStatus: 'failed',
                    error: new KiokeError({ code: ErrorCode.UNKNOWN_ERROR }),
                  }
                : image,
            ),
          );
          return;
        }

        return { imageId: uploadImageResponse.value.imageId };
      }),
    );

    updateAttributes({
      images: [
        ...newImageIds
          .filter((image) => image !== undefined)
          .map(({ imageId }) => ({
            imageId,
            description: '',
          })),
        ...imageData,
      ],
    });
  };

  if (!images || images.length === 0) {
    return (
      <NodeViewWrapper>
        <Card className='my-1'>
          <CardContent>
            <Tabs defaultValue='upload'>
              <TabsList>
                <TabsTrigger value='upload'>Upload Image</TabsTrigger>
                <TabsTrigger value='link'>Link</TabsTrigger>
              </TabsList>

              <TabsContent value='upload'>
                <Input
                  type='file'
                  accept='image/*'
                  onChange={fileInputChangeHandler}
                  multiple
                />
              </TabsContent>
              <TabsContent value='link'></TabsContent>
            </Tabs>
          </CardContent>
        </Card>
      </NodeViewWrapper>
    );
  }

  return (
    <NodeViewWrapper>
      <div className='flex justify-center overflow-x-auto gap-4 p-2'>
        {images.map((image, index) => {
          if (image.loadStatus === 'loaded') {
            const rowHeight = 150;

            return (
              <div
                key={index}
                className='relative'
                style={{
                  height: `${rowHeight}px`,
                  width: `${(rowHeight * image.width) / image.height}px`,
                }}
              >
                <NextImage
                  src={image.src}
                  alt={image.description}
                  fill
                  className='object-cover'
                />
              </div>
            );
          } else if (image.loadStatus === 'failed') {
            return <div key={index}>{image.error.message}</div>;
          }

          return <div key={index}></div>;
        })}
      </div>
    </NodeViewWrapper>
  );
}

export { ImageBlock, type ImageBlockAttributes };
