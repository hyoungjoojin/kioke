import { computePosition, flip, shift } from '@floating-ui/react';
import { Extension, ReactRenderer, posToDOMRect } from '@tiptap/react';
import type { Editor, Range } from '@tiptap/react';
import {
  Suggestion,
  type SuggestionOptions,
  type SuggestionProps,
} from '@tiptap/suggestion';
import {
  type RefAttributes,
  forwardRef,
  useImperativeHandle,
  useState,
} from 'react';

interface CommandPaletteItemProps {
  title: string;
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
                  title: 'Image',
                  command: ({ editor }) => {
                    editor.chain().insertContent({ type: 'image' }).run();
                  },
                },
                {
                  title: 'Map',
                  command: ({ editor }) => {
                    editor.chain().insertContent({ type: 'map' }).run();
                  },
                },
              ] as CommandPaletteItemProps[]
            )
              .filter((item) => {
                return item.title.toLowerCase().startsWith(query.toLowerCase());
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
      <div className='bg-card rounded-2xl w-20 flex flex-col'>
        {props.items.map((item, index) => {
          return (
            <button
              type='button'
              className={`${index === selectedIndex ? 'bg-red-500' : ''}`}
              key={index}
              onClick={() => {
                selectItem(index);
              }}
            >
              {item.title}
            </button>
          );
        })}
      </div>
    );
  },
);
CommandPalette.displayName = 'CommandPalette';
