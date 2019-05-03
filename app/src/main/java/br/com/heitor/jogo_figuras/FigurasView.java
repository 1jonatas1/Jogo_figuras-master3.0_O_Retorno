package br.com.heitor.jogo_figuras;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FigurasView extends View {
    private boolean telaCriada = false;
    private ArrayList<Point> ponteiro = new ArrayList<>();
    Point posCircle, posQuad, posTri, posRet;
    private float previousX;
    private float previousY;
    int pont = 0;
    int figuraEscolhida = 1;
    boolean gameStart = false;
    int toques = 0;

    public FigurasView(Context context) {
        super(context);
    }

    public FigurasView(Context context, AttributeSet attrs) {
        //Caused by: java.lang.NoSuchMethodException: <init> [class android.content.Context, interface android.util.AttributeSet]
        super(context, attrs); // This should be first line of constructor
    }

    public FigurasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    int width;
    int height;

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        final Paint paint = new Paint();
        if (telaCriada == false) {
            ponteiro = gerarPosições();
            telaCriada = true;
        }else{
            Random random = new Random();
            int aux = random.nextInt(4)+1;
            figuraEscolhida = aux;
            if (aux == 1){
                Toast.makeText(getContext(), "Toque no Quadrado" , Toast.LENGTH_SHORT).show();
            }
            else if (aux == 2){
                Toast.makeText(getContext(), "Toque no Retangulo" , Toast.LENGTH_SHORT).show();
            }
            else if (aux == 3){
                Toast.makeText(getContext(), "Toque no Circulo" , Toast.LENGTH_SHORT).show();
            }
            else if (aux == 4) {
                Toast.makeText(getContext(), "Toque no Triangulo" , Toast.LENGTH_SHORT).show();
            }
            gerarFiguras(canvas, paint, ponteiro);
        }

    }

    public ArrayList<Point> gerarPosições() {
        ArrayList<Point> Ponteiros = new ArrayList<>();
        width = getWidth();
        height = getHeight();
        Point posição1 = new Point(width / 4, 2 * height / 4);
        Point posição2 = new Point(width / 4, 3 * height / 4);
        Point posição3 = new Point(3 * width / 4, 2 * height / 4);
        Point posição4 = new Point(3 * width / 4, 3 * height / 4);
        Ponteiros.add(posição1);
        Ponteiros.add(posição2);
        Ponteiros.add(posição3);
        Ponteiros.add(posição4);
        Collections.shuffle(ponteiro);
        return Ponteiros;

    }

    public void gerarFiguras(Canvas canvas, Paint paint, ArrayList<Point> Ponteiros) {
        Collections.shuffle(ponteiro);
        Path path = new Path();
        int x = getWidth();
        int y = getHeight();
        int raio = 100;
        Random random = new Random();
        int a = random.nextInt(156) + 100;
        int r = random.nextInt(156) + 100;
        int g = random.nextInt(156) + 100;
        int b = random.nextInt(156) + 100;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        paint.setColor(Color.argb(a, r, g, b));
        //Circulo
        canvas.drawCircle(Ponteiros.get(0).x, Ponteiros.get(0).y, raio, paint);
        posCircle = ponteiro.get(0);
        //Quadrado
        canvas.drawRect((Ponteiros.get(1).x) - raio, (Ponteiros.get(1).y) - raio, raio + Ponteiros.get(1).x, raio + Ponteiros.get(1).y, paint);
        posQuad = ponteiro.get(1);
        //Triangulo
        // canvas.drawCircle(3*x/4,2*y/4,raio,paint);
        Point a1 = new Point(Ponteiros.get(2).x, Ponteiros.get(2).y - raio);
        Point b2 = new Point(raio + Ponteiros.get(2).x, raio + Ponteiros.get(2).y);
        Point c3 = new Point(Ponteiros.get(2).x - raio, raio + Ponteiros.get(2).y);
        posTri = ponteiro.get(2);
//        path.setFillType(Path.FillType.EVEN_ODD);

        path.lineTo(a1.x, a1.y);
        path.lineTo(b2.x, b2.y);
        path.lineTo(c3.x, c3.y);
        path.lineTo(a1.x, a1.y);
        path.close();

        canvas.drawPath(path, paint);
        //Retangulo

        canvas.drawRect((Ponteiros.get(3).x) - raio / 2, (Ponteiros.get(3).y) - raio, raio / 2 + Ponteiros.get(3).x, raio + Ponteiros.get(3).y, paint);
        posRet = ponteiro.get(3);
    }
    private Handler handler = new Handler();
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        int eventAction = e.getAction();
        float x = e.getX();
        float y = e.getY();
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                if(gameStart == true) {
                    if (x >= posQuad.x - 100 && x <= 100 + posQuad.x && y >= posQuad.y - 100 && y <= 100 + posQuad.y) {
                        if (figuraEscolhida == 1 && pont < 5) {
                            pont += 1;
                            Toast.makeText(getContext(), "Você acertou a figura e sua pontuação atualmente é de " + pont + " pontos", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pont > 0) {
                                pont -= 1;
                            }
                        }
                    } else if (x >= posRet.x - 100 / 2 && x <= 100 / 2 + posRet.x && y >= posRet.y - 100 && y <= 100 + posRet.y) {
                        if (figuraEscolhida == 2 && pont < 5) {
                            pont += 1;
                            Toast.makeText(getContext(), "Você acertou a figura e sua pontuação atualmente é de " + pont + " pontos", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pont > 0) {
                                pont -= 1;
                            }
                        }
                    } else if (x >= posCircle.x - 100 && x <= 100 + posCircle.x && y >= posCircle.y - 100 && y <= 100 + posCircle.y) {
                        if (figuraEscolhida == 3 && pont < 5) {
                            pont += 1;
                            Toast.makeText(getContext(), "Você acertou a figura e sua pontuação atualmente é de " + pont + " pontos", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pont > 0) {
                                pont -= 1;
                            }
                        }
                    } else if (x >= posTri.x - 100 && x <= 100 + posTri.x && y >= posTri.y - 100 && y <= 100 + posTri.y) {
                        if (figuraEscolhida == 4 && pont < 5) {
                            pont += 1;
                            Toast.makeText(getContext(), "Você acertou a figura e sua pontuação atualmente é de " + pont + " pontos", Toast.LENGTH_SHORT).show();

                        } else {
                            if (pont > 0) {
                                pont -= 1;
                            }
                        }
                    }
                }
                toques +=1;
                break;
        }

        if(toques>=5){
            Toast.makeText(getContext(), "Sua pontuação foi de um total de" + pont + " pontos", Toast.LENGTH_SHORT).show();
            pont = 0;
            toques = 0;
        }
        else{
            zerarTela();
        }
        return true;
    }

    public void setFiguraEscolhida(int figuraEscolhida) {
        this.figuraEscolhida = figuraEscolhida;
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }
    public void zerarTela(){
        postInvalidateDelayed(1000); 
    }

}
