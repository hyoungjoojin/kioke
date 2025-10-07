import type { BlockAttributes } from '.';
import { uploadImage } from '@/app/api/image';
import { Card, CardContent } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { BlockType } from '@/constant/block';
import { ErrorCode } from '@/constant/error';
import { cn } from '@/lib/utils';
import KiokeError from '@/util/error';
import {
  Node,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import { default as NextImage } from 'next/image';
import { type ChangeEvent, useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

type ImageBlockAttributes = BlockAttributes & {
  images: {
    imageId: string;
    imageUrl: string;
    width: number;
    height: number;
    description: string;
  }[];
};

const ImageBlock = Node.create({
  name: BlockType.IMAGE_BLOCK,
  group: 'block',
  content: '',
  atom: true,
  selectable: true,
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
  | { loadStatus: 'loaded'; src: string; width: number; height: number }
  | { loadStatus: 'failed'; error: KiokeError }
) &
  (
    | { uploadStatus: 'pending' | 'uploaded' }
    | { uploadStatus: 'failed'; error: KiokeError }
  );

function Image({ node, updateAttributes, selected }: NodeViewProps) {
  const { images: imageData } = node.attrs as ImageBlockAttributes;

  const [images, setImages] = useState<ImageState[]>(
    imageData.map((image) => ({
      ...image,
      loadStatus: 'loaded',
      src: image.imageUrl,
      width: image.width,
      height: image.height,
      uploadStatus: 'uploaded',
    })),
  );

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
        <Card className={cn('my-1', selected && 'bg-red-300/40 shadow-md')}>
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
