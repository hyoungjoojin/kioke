import { getImage } from '@/app/api/image/getImage';
import { uploadImage } from '@/app/api/image/uploadImage';
import { Card, CardContent } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Spinner } from '@/components/ui/spinner';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import logger from '@/lib/logger';
import { cn } from '@/lib/utils';
import { unwrap } from '@/util/result';
import {
  Node,
  NodeViewContent,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import { default as NextImage } from 'next/image';
import { type ChangeEvent, useEffect, useState } from 'react';

interface ImageNodeAttributes {
  mediaId: string | null;
  src: string | null;
}

export const ImageNode = Node.create({
  name: 'image',
  group: 'block',
  content: 'inline*',
  addAttributes() {
    return {
      mediaId: {
        default: null,
      },
      src: {
        default: null,
      },
    };
  },
  renderHTML({ HTMLAttributes }) {
    return ['kioke-image', mergeAttributes(HTMLAttributes)];
  },
  parseHTML() {
    return [
      {
        tag: 'kioke-image',
      },
    ];
  },
  addNodeView() {
    return ReactNodeViewRenderer(Image);
  },
});

function Image({ node, updateAttributes }: NodeViewProps) {
  const [isLoading, setIsLoading] = useState(false);
  const [isUploading, setIsUploading] = useState(false);
  const [isError, setIsError] = useState(false);
  const [imageSource, setImageSource] = useState<string | null>(null);

  const { mediaId, src } = node.attrs as ImageNodeAttributes;

  useEffect(() => {
    if (src) {
      setImageSource(src);
      return;
    }

    if (!mediaId) {
      setImageSource(null);
      return;
    }

    const fetchUrl = async () => {
      setIsLoading(true);
      await getImage(mediaId)
        .then((result) => unwrap(result))
        .then((url) => {
          setImageSource(url);
          setIsLoading(false);
        })
        .catch((error) => {
          logger.error(error);
          return '';
        });
    };

    fetchUrl();
  }, [src, mediaId, imageSource]);

  const fileUploadHandler = async (e: ChangeEvent<HTMLInputElement>) => {
    const selectedFiles = e.target.files;
    if (!selectedFiles || selectedFiles.length === 0) {
      return;
    }

    const fileToUpload = selectedFiles[0];

    updateAttributes({
      src: URL.createObjectURL(fileToUpload),
    });

    setIsUploading(true);

    await uploadImage(fileToUpload)
      .then((response) => unwrap(response))
      .then((mediaId) => {
        updateAttributes({
          src: null,
          mediaId,
        });
      })
      .catch((error) => {
        logger.error(error);
        setIsError(true);
        setIsUploading(false);
      });

    setIsUploading(false);
  };

  if (!imageSource) {
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
                  onChange={fileUploadHandler}
                />
              </TabsContent>

              <TabsContent value='link'></TabsContent>
            </Tabs>
          </CardContent>
        </Card>
        <NodeViewContent />
      </NodeViewWrapper>
    );
  }

  return (
    <NodeViewWrapper>
      <div>
        {isUploading && (
          <div className='absolute w-full h-full flex items-center justify-center z-99'>
            <div className='bg-background p-1 rounded-lg flex items-center gap-2'>
              <Spinner />
              <span className='text-sm'>Uploading Image</span>
            </div>
          </div>
        )}

        <div
          className={cn(
            'h-full w-full flex justify-center',
            isUploading && 'backdrop-blur-xl',
          )}
        >
          {isError ? (
            <div>Failed to load image</div>
          ) : isLoading ? (
            <div>Loading...</div>
          ) : imageSource ? (
            <NextImage
              src={imageSource}
              height={500}
              width={500}
              alt=''
              onError={() => {
                setIsUploading(false);
                setIsError(true);
              }}
            />
          ) : null}
        </div>
      </div>
    </NodeViewWrapper>
  );
}
