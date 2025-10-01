import Icon, { IconName } from '@/components/ui/icon';
import { Separator } from '@/components/ui/separator';
import { BlockType } from '@/constant/block';
import { cn } from '@/lib/utils';
import { computePosition, flip, shift } from '@floating-ui/react';
import { Extension, ReactRenderer, posToDOMRect } from '@tiptap/react';
import type { Editor, Range } from '@tiptap/react';
import {
  Suggestion,
  type SuggestionOptions,
  type SuggestionProps,
} from '@tiptap/suggestion';
import { useTranslations } from 'next-intl';
import {
  type RefAttributes,
  forwardRef,
  useImperativeHandle,
  useState,
} from 'react';

interface CommandPaletteItemProps {
  name: string;
  icon: IconName;
  command: (props: { editor: Editor; range: Range }) => void;
}

interface CommandPaletteExtensionOptions {
  suggestion: Partial<SuggestionOptions<CommandPaletteItemProps>>;
}

function updatePosition(editor: Editor, element: HTMLElement) {
  const velement = {
    getBoundingClientRect: () =>
      posToDOMRect(
        editor.view,
        editor.state.selection.from,
        editor.state.selection.to,
      ),
  };

  computePosition(velement, element, {
    placement: 'bottom-start',
    strategy: 'absolute',
    middleware: [shift(), flip()],
  }).then(({ x, y, strategy }) => {
    element.style.width = 'max-content';
    element.style.position = strategy;
    element.style.left = `${x}px`;
    element.style.top = `${y}px`;
  });
}

export const CommandPaletteExtension =
  Extension.create<CommandPaletteExtensionOptions>({
    name: 'commandPalette',
    addOptions() {
      return {
        suggestion: {
          char: '/',
          command({ editor, range, props }) {
            props.command({ editor, range });
          },
          items({ query }) {
            return (
              [
                {
                  name: 'image',
                  icon: IconName.IMAGE,
                  command: ({ editor, range }) => {
                    editor
                      .chain()
                      .deleteRange(range)
                      .insertContent([
                        { type: BlockType.IMAGE_BLOCK },
                        { type: BlockType.TEXT_BLOCK },
                      ])
                      .joinForward()
                      .run();
                  },
                },
                {
                  name: 'map',
                  icon: IconName.PAINT,
                  command: ({ editor, range }) => {
                    editor
                      .chain()
                      .deleteRange(range)
                      .insertContent({ type: 'map' })
                      .run();
                  },
                },
              ] as CommandPaletteItemProps[]
            )
              .filter((item) => {
                return item.name.startsWith(query.toLowerCase());
              })
              .slice(0, 10);
          },
          startOfLine: false,
          allow: ({ editor }) => {
            if (!editor.isFocused) {
              return false;
            }

            return true;
          },
          render: () => {
            let renderer: ReactRenderer<
              CommandPaletteRef,
              CommandPaletteProps & RefAttributes<CommandPaletteRef>
            > | void;

            return {
              onStart(props) {
                if (!props.clientRect) {
                  return;
                }

                renderer = new ReactRenderer(CommandPalette, {
                  props,
                  editor: props.editor,
                });

                (renderer.element as HTMLElement).style.position = 'absolute';
                document.body.appendChild(renderer.element);
                updatePosition(props.editor, renderer.element as HTMLElement);
              },
              onUpdate(props) {
                if (!renderer) {
                  return;
                }

                if (props.query.length >= 6 || props.items.length === 0) {
                  if (renderer) {
                    renderer.element.remove();
                    renderer.destroy();
                  }
                  return;
                }

                renderer.updateProps(props);

                if (!props.clientRect) {
                  return;
                }

                updatePosition(props.editor, renderer.element as HTMLElement);
              },
              onKeyDown(props) {
                if (props.event.key === 'Escape') {
                  if (renderer) {
                    renderer.element.remove();
                    renderer.destroy();
                  }

                  return true;
                }

                return renderer?.ref?.onKeyDown(props) ?? false;
              },
              onExit: () => {
                if (renderer) {
                  renderer.element.remove();
                  renderer.destroy();
                }
              },
            };
          },
        },
      };
    },
    addProseMirrorPlugins() {
      return [
        Suggestion<CommandPaletteItemProps>({
          editor: this.editor,
          ...this.options.suggestion,
        }),
      ];
    },
  });

interface CommandPaletteRef {
  onKeyDown: (props: { event: KeyboardEvent }) => boolean;
}

type CommandPaletteProps = SuggestionProps<CommandPaletteItemProps>;

const CommandPalette = forwardRef<CommandPaletteRef, CommandPaletteProps>(
  (props, ref) => {
    const t = useTranslations('editor.command-palette');

    const [selectedIndex, setSelectedIndex] = useState(0);

    const selectItem = (index: number) => {
      const item = props.items[index];
      if (item) {
        props.command(item);
      }
    };

    useImperativeHandle(ref, () => ({
      onKeyDown: ({ event }) => {
        if (event.key === 'ArrowUp') {
          setSelectedIndex(
            (selectedIndex + props.items.length - 1) % props.items.length,
          );
          return true;
        }

        if (event.key === 'ArrowDown') {
          setSelectedIndex((selectedIndex + 1) % props.items.length);
          return true;
        }

        if (event.key === 'Enter') {
          selectItem(selectedIndex);
          return true;
        }

        return false;
      },
    }));

    return (
      <div className='bg-card rounded-2xl flex flex-col'>
        {props.items.map((item, index) => {
          return (
            <div
              key={index}
              className={cn(
                'flex items-center ',
                index === selectedIndex ? 'bg-red-400/30' : 'bg-background',
                index === 0 && 'rounded-t-2xl',
              )}
              onClick={() => {
                selectItem(index);
              }}
            >
              <div className='flex items-center justify-center p-2'>
                <Icon name={item.icon} />
              </div>

              <div className='flex flex-col'>
                <span className='text-sm'>{t(`${item.name}.title`)}</span>
                <span className='text-xs'>{t(`${item.name}.description`)}</span>
              </div>
            </div>
          );
        })}

        <Separator />

        <div className='flex justify-between items-center px-3 py-2'>
          <span className='text-sm'>
            Type {"'"}/{props.query}
            {"'"} on the page
          </span>

          <span className='text-xs'>ESC</span>
        </div>
      </div>
    );
  },
);
CommandPalette.displayName = 'CommandPalette';
