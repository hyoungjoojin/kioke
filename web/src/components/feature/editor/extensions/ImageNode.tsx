import { Card, CardContent } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import {
  Node,
  NodeViewContent,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';

export const ImageNode = Node.create({
  name: 'image',
  group: 'block',
  content: 'inline*',
  addAttributes() {
    return {
      src: {
        default: null,
      },
      count: {
        default: 0,
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
  const src = node.attrs.src;

  if (!src) {
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
                <Input type='file' />
              </TabsContent>

              <TabsContent value='link'></TabsContent>
            </Tabs>
          </CardContent>
        </Card>
        <NodeViewContent />
      </NodeViewWrapper>
    );
  }

  return <>image</>;
}
